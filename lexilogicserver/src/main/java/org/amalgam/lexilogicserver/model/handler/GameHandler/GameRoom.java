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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameRoom implements NTimerCallback {

    private int roomID, currentRound, secondsRoundDuration;
    private LinkedHashMap<String,PlayerGameDetail> details;
    private final LinkedHashMap<String,PlayerGameDetail> defaultDetails;
    private boolean roundDone;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private WordBox wordBox;
    private LinkedHashMap<Integer, String> rounds = new LinkedHashMap<>();
    private LinkedHashMap<String,PlayerCallback> playerCallbacks = new LinkedHashMap<>();
    private LinkedHashMap<String, Integer> totalPointsPerPlayer = new LinkedHashMap<>();



    public GameRoom(int roomID, LinkedHashMap<String,PlayerGameDetail> details,LinkedHashMap<String,PlayerCallback> playerCallbacks ,int secondsRoundDuration) throws FileNotFoundException {
        this.roomID = roomID;
        this.defaultDetails = details;
        this.secondsRoundDuration = secondsRoundDuration;
        this.playerCallbacks = playerCallbacks;
        currentRound = 1;
        generateWordBox();
        //stagePlayers();

    }

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

    public void stagePlayers() {
        System.out.println("Staging, "+currentRound);
        details = new LinkedHashMap<>();
        details = flushWith(defaultDetails); // resets details to default unready state
        String w;
        if((w = winnerAvailable())!=null){
            try{
                String response = GameRoomResponseBuilder.buildWinnerResponse(w); // Use response builder for this, broadcast state game done, + winner(variable w)
                broadcast(response);
                int lobbyID = LobbyDAL.insertGameRoomAsLobby(this);
                updatePlayerDataDB(lobbyID);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        roundDone = false;
        //give initial gameroom object + state = staging(countdown 5 secs then send request ready)
        String jsonString = GameRoomResponseBuilder.buildStagePlayersResponse(this,5); //Use response builder here
        try {
            broadcast(jsonString);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public char[][] getCharMatrix (){
        return wordBox.getWordMatrix();
    }

    public void updatePlayerDataDB(int lobbyID){
        List<String> keys = new ArrayList<>(details.keySet());
        for(String key: keys){
            PlayerGameDetail detail = details.get(key);
            GameDetailDAL.insertGameDetailFromPlayerDetail(detail, lobbyID);
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

    public void submitWord(String word, String username){
        try {
            if (roundDone) {
                return;
            }
            if (checkIfDupe(word)) {
                broadcast(username, GameRoomResponseBuilder.buildInvalidWordResponse());
                return;
            } // should just throw exception of duped word

            if (wordBox.verifyWord(word) == 0) {
                broadcast(username, GameRoomResponseBuilder.buildInvalidWordResponse());
                return;
            } // should just throw exception

            PlayerGameDetail gameDetail = details.get(username);
            gameDetail.addWord(word);
            details.replace(username, gameDetail);

            updatePoints(username);
        }catch(Exception e){
            e.printStackTrace();
        }
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

    public void broadcast(String jsonString) throws InvalidRequestException {
        List<String> keys = new ArrayList<>(playerCallbacks.keySet());
        for(String key: keys){
            PlayerCallback callback = playerCallbacks.get(key);
            callback.uiCall(jsonString);
        }
    }

    //Method overload
    public void broadcast(String username, String jsonString) throws InvalidRequestException{
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
    private String winnerAvailable(){
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
                    winner.append(value);
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

    public void tallyRoundTotalPoints(){
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



    // TODO: should check if winner is available then tell players in game room winner has been decided and a new
    //          round has started
    @Override
    public void timerDone() {
        System.out.println("round done");
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
        stagePlayers();
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

    private LinkedHashMap<String, PlayerGameDetail> flushWith(LinkedHashMap<String, PlayerGameDetail> defaultValues){
        LinkedHashMap<String, PlayerGameDetail> flushedData = new LinkedHashMap<>();
        List<String> keyList = new ArrayList<>(defaultValues.keySet());
        for(String key : keyList){
            PlayerGameDetail clone = new PlayerGameDetail(defaultValues.get(key).getUsername());
            flushedData.put(key, clone);
        }

        return flushedData;
    }
}
