package org.amalgam.lexilogicserver.model.utilsimpl;

import org.amalgam.Utils.ObjectExceptions.PlayerAlreadyInRoom;
import org.amalgam.Utils.ObjectExceptions.PlayerListRetrievalException;
import org.amalgam.Utils.ObjectExceptions.RoomDoesNotExistException;
import org.amalgam.Utils.ObjectExceptions.SQLError;
import org.amalgam.Utils.Objects.GameRoomPOA;

import java.util.LinkedList;

public class GameRoomImpl extends GameRoomPOA {
    private int roomID;
    private LinkedList<String> players = new LinkedList<>();


    @Override
    public int roomID() {
        return this.roomID;
    }

    @Override
    public void roomID(int newRoomID) {
        this.roomID = newRoomID;
    }

    @Override
    public String[] players() {
        String[] playerArray = new String[this.players.size()];
        int index = 0;
        for(String player : players){
            playerArray[index] = player;
            index++;
        }
        return playerArray;
    }

    @Override
    public void players(String[] newPlayers) {
        this.players.clear();
        for(String player : newPlayers){
            this.players.add(player);
        }
    }

    @Override
    public void addPlayer(String playerName) throws PlayerAlreadyInRoom, SQLError {
        this.players.add(playerName);
    }

    @Override
    public int getRoomID() throws RoomDoesNotExistException {
        return this.roomID;
    }

    @Override
    public String[] getPlayers() throws PlayerListRetrievalException {
        String[] playerArray = new String[this.players.size()];
        int index = 0;
        for(String player : players){
            playerArray[index] = player;
            index++;
        }
        return playerArray;
    }
}
