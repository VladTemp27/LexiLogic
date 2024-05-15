package org.amalgam.lexilogicserver.model.handler.GameHandler;

import org.amalgam.ControllerException.InvalidRequestException;
import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.lexilogicserver.model.microservices.NTimer;
import org.amalgam.lexilogicserver.model.microservices.NTimerCallback;
import org.amalgam.lexilogicserver.model.microservices.wordbox.Generator;
import org.amalgam.lexilogicserver.model.microservices.wordbox.Reader;
import org.amalgam.lexilogicserver.model.microservices.wordbox.WordBox;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.GameDetail;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.PlayerGameDetail;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameRoom implements NTimerCallback {

    private int roomID, currentRound, secondsRoundDuration;
    private LinkedHashMap<String,PlayerGameDetail> details, defaultDetails;
    private boolean roundDone;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private WordBox wordBox;
    private LinkedHashMap<Integer, String> rounds = new LinkedHashMap<>();
    private LinkedHashMap<String,PlayerCallback> playerCallbacks = new LinkedHashMap<>();



    public GameRoom(int roomID, LinkedHashMap<String,PlayerGameDetail> details,LinkedHashMap<String,PlayerCallback> playerCallbacks ,int secondsRoundDuration) throws FileNotFoundException {
        this.roomID = roomID;
        this.defaultDetails = details;
        this.secondsRoundDuration = secondsRoundDuration;
        this.playerCallbacks = playerCallbacks;
        currentRound = 1;
        rounds.put(currentRound, null);
        generateWordBox();
        stagePlayers();

    }

    public void setPlayerReady(String username){
        PlayerGameDetail detail = details.get(username);
        detail.setReady(true);

        if(!isAllPlayersReady()){
            return;
        }

        roundStart();
    }

    private void stagePlayers() {
        details = defaultDetails; // resets details to default unready state
        String w;
        if((w = winnerAvailable())!=null){
            try{
                String response = ""; // Use response builder for this, broadcast state game done, + winner(variable w)
                broadcast(response);
                //TODO convert this to reference objects and push to database using DAL
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        roundDone = false;
        //give initial gameroom object + state = staging(countdown 5 secs then send request ready)
        String jsonString = ""; //Use response builder here
        try {
            broadcast(jsonString);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void roundStart(){
        try {
            //Use GameRoomResponseBuilder here to tell clients state is game started + game rooms 😁
            String data = "";

            broadcast(data);
        }catch(InvalidRequestException e){
            System.out.println(e.getMessage());
        }
        executor.submit(new NTimer(secondsRoundDuration, this));
    }

    private boolean isAllPlayersReady(){
        List<String> keys = new ArrayList<>(details.keySet());
        for(String key: keys){
            PlayerGameDetail currentPlayer = details.get(key);
            if(!currentPlayer.isReady()){
                return false;
            }
        }
        return true;
    }

    //call this to generate a wordBox, generates a new wordbox for every invocation
    private void generateWordBox() throws FileNotFoundException {
        wordBox = new WordBox(new Generator(new Reader("file"), true, 6, 6));
    }

    public void submitWord(String word, String username){
        if(roundDone){
            return;
        }
        if(checkIfDupe(word)) return; // should just throw exception of duped word

        if(wordBox.verifyWord(word)==0)return; // should just throw exception

        details.get(username).addWord(word);    // should update using replace

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
        for(int x = 1; x < rounds.size(); x++){
            String roundWinner = rounds.get(x);
            if(roundWinners.containsKey(roundWinner)){
                int initialVal = roundWinners.get(roundWinner);
                roundWinners.replace(roundWinner, initialVal+1);
                continue;
            }
            roundWinners.put(roundWinner, 1);
        }

        if(roundWinners.containsValue(3)){
            roundWinners.forEach((key, value) -> {
                if(value==3){
                    winner.append(value);
                }
            });
            return winner.toString();
        }
        return null;
    }


    // TODO: should check if winner is available then tell players in game room winner has been decided and a new
    //          round has started
    @Override
    public void timerDone() {
        this.roundDone = true;
        stagePlayers();
    }

    @Override
    public void timeIs() {

    }
}
