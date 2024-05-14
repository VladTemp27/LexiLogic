package org.amalgam.lexilogicserver.model.utilities.referenceobjects;

import org.amalgam.lexilogicserver.model.microservices.NTimer;
import org.amalgam.lexilogicserver.model.microservices.NTimerCallback;
import org.amalgam.lexilogicserver.model.microservices.wordbox.Generator;
import org.amalgam.lexilogicserver.model.microservices.wordbox.Reader;
import org.amalgam.lexilogicserver.model.microservices.wordbox.WordBox;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameRoom implements NTimerCallback {
    private int roomID;
    private LinkedList<PlayerGameDetail> details;
    private boolean gameDone;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private WordBox wordBox;

    public GameRoom(int roomID, LinkedList<PlayerGameDetail> details, int minGameDuration) {
        this.roomID = roomID;
        this.details = details;
        executor.submit(new NTimer(minGameDuration*60, this));
    }

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

    public boolean isGameDone() {
        return gameDone;
    }

    public void addPlayerDetail(String username){
        PlayerGameDetail playerDetail = new PlayerGameDetail(username);
        details.add(playerDetail);
    }

    @Override
    public void timerDone() {
        this.gameDone = true;
    }
}
