package org.amalgam.lexilogicserver.model.handler.GameHandler;

import org.amalgam.ControllerException.InvalidRequestException;
import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.lexilogicserver.model.DAL.GameDetailDAL;
import org.amalgam.lexilogicserver.model.DAL.LeaderBoardDAL;
import org.amalgam.lexilogicserver.model.DAL.LobbyDAL;
import org.amalgam.lexilogicserver.model.microservices.NTimer;
import org.amalgam.lexilogicserver.model.microservices.NTimerCallback;
import org.amalgam.lexilogicserver.model.microservices.wordbox.Generator;
import org.amalgam.lexilogicserver.model.microservices.wordbox.Reader;
import org.amalgam.lexilogicserver.model.microservices.wordbox.WordBox;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.PlayerGameDetail;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameRoom implements NTimerCallback {
    private int roomID, currentRound, secondsRoundDuration, capacity;
    private boolean roundDone;
    private WordBox wordBox;
    private LinkedHashMap<String,PlayerGameDetail> details;
    private LinkedHashMap<Integer, String> rounds = new LinkedHashMap<>();
    private LinkedHashMap<String,PlayerCallback> playerCallbacks = new LinkedHashMap<>();
    private LinkedHashMap<String, Integer> totalPointsPerPlayer = new LinkedHashMap<>();
    private final LinkedHashMap<String,PlayerGameDetail> defaultDetails;

    private ConcurrentHashMap<String, Boolean> readyToReceive = new ConcurrentHashMap<String, Boolean>();
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public GameRoom(int roomID, LinkedHashMap<String,PlayerGameDetail> details, LinkedHashMap<String,PlayerCallback> playerCallbacks , int secondsRoundDuration, int capacity) throws FileNotFoundException {
        this.roomID = roomID;
        this.defaultDetails = details;
        this.secondsRoundDuration = secondsRoundDuration;
        this.playerCallbacks = playerCallbacks;
        currentRound = 1;
        this.capacity = capacity;
        generateWordBox();
        initializeReadyToReceive(playerCallbacks);
        //stagePlayers();
    }

    private void initializeReadyToReceive(LinkedHashMap<String, PlayerCallback> callbacks){
        for(String key: callbacks.keySet()){
            readyToReceive.put(key, false);
        }
    }

    public synchronized void markPlayerReadyToReceive(String username){
        System.out.println("Marking "+username+" ready for response");
        readyToReceive.replace(username,true);
        System.out.println("All Players Ready to Receive: "+(!readyToReceive.contains(false)));
        if(readyToReceive.contains(false)){
            return;
        }

        System.out.println("Staging players");
        stagePlayers();
    }

    //This method is invoked to mark the players in the room as ready
    // TODO: explore if concurrently accessing hashmaps would pose a problem
    public void setPlayerReady(String username){
        PlayerGameDetail detail = details.get(username);
        detail.setReady(true);

        System.out.println(username +" triggered ready");
        if(!isAllPlayersReady()){
            System.out.println("Waiting for other players");
            return;
        }

        System.out.println("triggering round start");
        roundStart();
    }

    //This method is invoked to set player states into a staging. This method is the basis for the round countdown
    public void stagePlayers() {
        System.out.println("Staging, "+currentRound);
        details = new LinkedHashMap<>();
        details = flushWith(defaultDetails); // resets details to default unready state
        roundDone = false;
        //give initial gameroom object + state = staging(countdown 5 secs then send request ready)
        String jsonString = GameRoomResponseBuilder.buildStagePlayersResponse(this,5); //Use response builder here
        System.out.println(jsonString);
        try {
            Thread.sleep(400);
            broadcast(jsonString);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //Getter for the charMatrix of the Room
    public char[][] getCharMatrix (){
        return wordBox.getWordMatrix();
    }

    private void updatePlayerDataDB(int lobbyID){
        List<String> keys = new ArrayList<>(details.keySet());
        for(String key: keys){
            PlayerGameDetail detail = details.get(key);
            GameDetailDAL.insertNewGameDetail(key, lobbyID, totalPointsPerPlayer.get(key));
            LeaderBoardDAL.updateLeaderBoard(detail);
        }
    }

    private void roundStart(){
        System.out.println("round start");
        try {
            //Use GameRoomResponseBuilder here to tell clients state is game started + game rooms 😁
            String data = GameRoomResponseBuilder.buildGameStartedResponse(this);
            broadcast(data);
        }catch(InvalidRequestException e){
            System.out.println(e.getMessage());
        }
        executor.submit(new NTimer(secondsRoundDuration, this));
        System.out.println("New timer submitted with duration "+secondsRoundDuration+"s");
    }

    //Checks if all players are ready
    private boolean isAllPlayersReady(){
        List<String> keys = new ArrayList<>(details.keySet());
        for(String key: keys){
            PlayerGameDetail currentPlayer = details.get(key);
            if(!currentPlayer.isReady()){
                System.out.println("returned false");
                return false;
            }
        }
        System.out.println("executed return true;");
        return true;
    }

    //call this to generate a wordBox, generates a new wordbox for every invocation
    private void generateWordBox() throws FileNotFoundException {
        wordBox = new WordBox(new Generator(new Reader("lexilogicserver/src/main/java/org/amalgam/lexilogicserver/model/microservices/wordbox/words.txt"), false, 4, 5));
    }

    //Method to be invoked once words are submitted via the game service request
    public void submitWord(String word, String username){
        System.out.println("Game room received request of word "+word);
        try {
            System.out.println("Round status :"+roundDone);
            if (roundDone) {
                return;
            }
            System.out.println("Checking word length");
            System.out.println(word.length()==4);
            if(word.length()==4){
                System.out.println("word too short");
                broadcast(username, GameRoomResponseBuilder.buildInvalidWordResponse());
            }

            System.out.println("Checking if dupe");
            System.out.println(checkIfDupe(word));
            if (checkIfDupe(word)) {
                System.out.println("Duped word");
                broadcast(username, GameRoomResponseBuilder.buildInvalidWordResponse());
                return;
            } // should just throw exception of duped word

            System.out.println("Checking if valid word");
            System.out.println(wordBox.verifyWord(word)==0);
            if (wordBox.verifyWord(word) == 0) {
                System.out.println("invalid word");
                broadcast(username, GameRoomResponseBuilder.buildInvalidWordResponse());
                return;
            } // should just throw exception

            System.out.println("Adding to details");
            PlayerGameDetail gameDetail = details.get(username);
            gameDetail.addWord(word);
            details.replace(username, gameDetail);

            System.out.println("Updating points");
            updatePoints(username);
            broadcast(GameRoomResponseBuilder.buildGameStartedResponse(this));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //Triggered by NTimer callback to notify parent class that timer is done, this is used to keep track of time elapsed
    // This method handles game flow once a round has finished
    @Override
    public void timerDone() {
        System.out.println("ROUND DONE"+currentRound);
        this.roundDone = true;
        String roundWinner = getRoundWinner();
        System.out.println("Winner: "+roundWinner);
        rounds.put(currentRound, roundWinner);
        System.out.println("winner saved");
        tallyRoundTotalPoints();
        currentRound++;
        System.out.println("next round: "+currentRound);
        try {
            generateWordBox();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        //Use broadcast with builder
        String w;
        if((w = winnerAvailable())!=null){
            System.out.println("----ROOM:"+this.roomID+" GAME ENDED WINNER:"+w+"----");
            try{
                String response = GameRoomResponseBuilder.buildWinnerResponse(w); // Use response builder for this, broadcast state game done, + winner(variable w)
                broadcast(response);
                int lobbyID = LobbyDAL.insertGameRoomAsLobby(this);
                updatePlayerDataDB(lobbyID);
                return;
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        stagePlayers();
    }

    private void updatePoints(String username){

        PlayerGameDetail detail = details.get(username);
        int pts = calculatePoints(detail.getWords());
        detail.setPoints(pts);
        details.replace(username, detail);

    }

    private int calculatePoints(LinkedList<String> listOfWords){
        int pts = 0;
        for(String word : listOfWords){
            pts += word.length();
        }
        return pts;
    }

    private void broadcast(String jsonString) throws InvalidRequestException {
        List<String> keys = new ArrayList<>(playerCallbacks.keySet());
        for(String key: keys){
            PlayerCallback callback = playerCallbacks.get(key);
            callback.uiCall(jsonString);
            System.out.println(callback.username() + " " + jsonString);
        }
    }

    //Method overload
    private void broadcast(String username, String jsonString) throws InvalidRequestException{
        List<String> keys = new ArrayList<>(playerCallbacks.keySet());
        for(String key: keys){
            if(!key.equals(username)){
                continue;
            }
            PlayerCallback callback = playerCallbacks.get(key);
            callback.uiCall(jsonString);
        }
    }


    //Check if word submitted is not unique
    private boolean checkIfDupe(String submittedWord){
        List<String> keys = new ArrayList<>(details.keySet());
        for(String key : keys){
            PlayerGameDetail gameDetail = details.get(key);
            if(gameDetail.listOfWordsContains(submittedWord)){
                markWordAsDuped(submittedWord);
                return true;
            }
        }
        return false;
    }

    private void markWordAsDuped(String dupeWord){
        List<String> keys = new ArrayList<>(details.keySet());
        for(String key : keys){
            PlayerGameDetail gameDetail = details.get(key);
            gameDetail.addDupedWord(dupeWord);
        }
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public LinkedHashMap<String, PlayerGameDetail> getDetails() {
        return details;
    }

    //Might not be needed
    public boolean isRoundDone() {
        return roundDone;
    }

    //Checker if winner is available returns null if winner is nut available
    public String winnerAvailable(){
        StringBuilder winner = new StringBuilder();
        LinkedHashMap<String, Integer> roundWinners = new LinkedHashMap<>();
        for(int x = 1; x <= rounds.size(); x++){
            String roundWinner = rounds.get(x);
            if(roundWinner.equals("No Winner")){
                continue;
            }
            if(roundWinners.containsKey(roundWinner)){
                int initialVal = roundWinners.get(roundWinner);
                roundWinners.replace(roundWinner, initialVal+1);
                continue;
            }
            roundWinners.put(roundWinner, 1);
        }

        if (roundWinners.containsValue(3)) {
            roundWinners.forEach((key, value) -> {
                if (value == 3) {
                    winner.append(key);
                }
            });
            return winner.toString();
        }
        return null;
    }

    private String getRoundWinner(){
        List<String> keys = new ArrayList<>(details.keySet());
        String keyWithMaxValue = null;
        int maxPoints = 0;
        //Gets highest value
        for(String key : keys){
            if(keyWithMaxValue==null){
                keyWithMaxValue = key;
                maxPoints = details.get(keyWithMaxValue).getPoints();
                continue;
            }
            int currentPoints = details.get(key).getPoints();
            if(currentPoints>maxPoints){
                keyWithMaxValue = key;
                maxPoints = details.get(keyWithMaxValue).getPoints();
            }
        }

        //Finds if another instance of this highest value is available (tie)
        boolean tie = false;
        for(String key : keys){
            if(key.equals(keyWithMaxValue)){
                continue;
            }
            if(details.get(key).getPoints()==details.get(keyWithMaxValue).getPoints()){
                tie = true;
            }
        }

        //Guard clause for tie situation
        if(tie){
            return "No Winner";
        }

        //Guard clause for 0 pts
        if(maxPoints==0){
            return "No Winner";
        }
        return keyWithMaxValue;
    }

    private void tallyRoundTotalPoints(){
        List<String> keys = new ArrayList<>(details.keySet());
        System.out.println(!totalPointsPerPlayer.isEmpty());
        if(!totalPointsPerPlayer.isEmpty()) {
            for (String key : keys) {
                System.out.println(keys);
                PlayerGameDetail playerGameDetail = details.get(key);
                System.out.println(key+" Pts: "+playerGameDetail.getPoints());
                totalPointsPerPlayer.replace(key, playerGameDetail.getPoints());
            }
            return;
        }

        for(String key : keys){
            PlayerGameDetail playergameDetail = details.get(key);
            System.out.println(key+" Pts: "+playergameDetail.getPoints());
            totalPointsPerPlayer.put(key, playergameDetail.getPoints());
        }

    }



    @Override
    public void timeIs() {

    }

    public LinkedHashMap<Integer, String> getRounds() {
        return rounds;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getSecondsRoundDuration() {
        return secondsRoundDuration;
    }


    //This method flushes the existing LinkedHashMap with new Objects to avoid overflow of data into existing memory
    private LinkedHashMap<String, PlayerGameDetail> flushWith(LinkedHashMap<String, PlayerGameDetail> defaultValues){
        LinkedHashMap<String, PlayerGameDetail> flushedData = new LinkedHashMap<>();
        List<String> keyList = new ArrayList<>(defaultValues.keySet());
        for(String key : keyList){
            PlayerGameDetail clone = new PlayerGameDetail(defaultValues.get(key).getUsername());
            flushedData.put(key, clone);
        }

        return flushedData;
    }

    public int getCapacity() {
        return capacity;
    }
}
