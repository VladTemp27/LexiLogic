package org.amalgam.lexilogicserver.model.utilities.referenceobjects;

import java.util.LinkedList;

public class GameRoom {
    private int roomID;
    private LinkedList<PlayerGameDetail> details;

    public GameRoom(int roomID, LinkedList<PlayerGameDetail> details) {
        this.roomID = roomID;
        this.details = details;
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

    public void addPlayerDetail(String username){
        PlayerGameDetail playerDetail = new PlayerGameDetail(username);
        details.add(playerDetail);
    }
}
