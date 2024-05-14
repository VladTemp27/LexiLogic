package org.amalgam.lexilogicserver.model.utilities.referenceobjects;

import org.amalgam.lexilogicserver.model.microservices.NTimer;
import org.amalgam.lexilogicserver.model.microservices.NTimerCallback;
import org.amalgam.lexilogicserver.model.microservices.wordbox.Generator;
import org.amalgam.lexilogicserver.model.microservices.wordbox.Reader;
import org.amalgam.lexilogicserver.model.microservices.wordbox.WordBox;

import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameRoom implements NTimerCallback {

    private int roomID;
    private LinkedList<PlayerGameDetail> details;
    private boolean roundDone;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private WordBox wordBox;
    private LinkedHashMap<Integer, String> rounds = new LinkedHashMap<>();


    public GameRoom(int roomID, LinkedList<PlayerGameDetail> details, int secondsRoundDuration) throws FileNotFoundException {
        this.roomID = roomID;
        this.details = details;
        rounds.put(1, null);
        generateWordBox();
        executor.submit(new NTimer(secondsRoundDuration, this));
    }

    //call this to generate a wordBox, generates a new wordbox for every invocation
    private void generateWordBox() throws FileNotFoundException {
        wordBox = new WordBox(new Generator(new Reader("file"), true, 6, 6));
    }

    public void submitWord(String word, String username){
        int userIndex = getIndexByUsername(username);
        if(userIndex<0){
            return; //instead of return should just throw some kind of exception
        }
        if(checkIfDupe(word)) return; // should just throw exception of duped word

        if(wordBox.verifyWord(word)==0)return; // should just throw exception

        details.get(userIndex).addWord(word);

    }

    private int getIndexByUsername(String username){
        for(PlayerGameDetail detail : details){
            if(detail.getUsername().equals(username)){
                return details.indexOf(detail);
            }
        }
        return -1;
    }

    //Check if word submitted is not unique
    private boolean checkIfDupe(String submittedWord){
        for(PlayerGameDetail gameDetail : details){
            if(gameDetail.listOfWordsContains(submittedWord)){
                markWordAsDuped(submittedWord);
                return true;
            }
        }
        return false;
    }

    private void markWordAsDuped(String dupeWord){
        for(PlayerGameDetail gameDetail : details){
            gameDetail.addDupedWord(dupeWord);
        }
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public LinkedList<PlayerGameDetail> getDetails() {
        return details;
    }

    //Might not be needed
    public boolean isRoundDone() {
        return roundDone;
    }

    public void addPlayerDetail(String username){
        PlayerGameDetail playerDetail = new PlayerGameDetail(username);
        details.add(playerDetail);
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
    }
}
