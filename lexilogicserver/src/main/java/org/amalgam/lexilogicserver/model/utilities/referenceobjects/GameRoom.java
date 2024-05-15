package org.amalgam.lexilogicserver.model.utilities.referenceobjects;

import org.amalgam.lexilogicserver.model.microservices.NTimer;
import org.amalgam.lexilogicserver.model.microservices.NTimerCallback;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameRoom implements NTimerCallback {
    private int roomID;
    private LinkedList<PlayerGameDetail> details;
    private boolean gameDone;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public GameRoom(int roomID, LinkedList<PlayerGameDetail> details, int minGameDuration) {
        this.roomID = roomID;
        this.details = details;
        executor.submit(new NTimer(minGameDuration*60, this));
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
