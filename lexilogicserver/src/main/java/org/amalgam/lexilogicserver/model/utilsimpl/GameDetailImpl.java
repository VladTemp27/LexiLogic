package org.amalgam.lexilogicserver.model.utilsimpl;

import org.amalgam.Utils.ObjectExceptions.InvalidPointsException;
import org.amalgam.Utils.ObjectExceptions.LobbyDoesNotExistException;
import org.amalgam.Utils.ObjectExceptions.PlayerDoesNotExistException;
import org.amalgam.Utils.ObjectExceptions.SQLError;
import org.amalgam.Utils.Objects.GameDetailPOA;

public class GameDetailImpl extends GameDetailPOA {
    private int playerID;
    private int lobbyID;
    private int totalPoints;
    @Override
    public int playerID() {
        return this.playerID;
    }

    @Override
    public void playerID(int newPlayerID) {
        this.playerID = newPlayerID;
    }

    @Override
    public int lobbyID() {
        return this.lobbyID;
    }

    @Override
    public void lobbyID(int newLobbyID) {
        this.lobbyID = newLobbyID;
    }

    @Override
    public int totalPoints() {
        return this.totalPoints;
    }

    @Override
    public void totalPoints(int newTotalPoints) {
        this.totalPoints = newTotalPoints;
    }

    @Override
    public void updateTotalPoints(int newPoints) throws InvalidPointsException, SQLError {
        this.totalPoints += newPoints;
    }

    @Override
    public int getPlayerID() throws PlayerDoesNotExistException {
        return this.playerID;
    }

    @Override
    public int getLobbyID() throws LobbyDoesNotExistException {
        return this.lobbyID;
    }
}
