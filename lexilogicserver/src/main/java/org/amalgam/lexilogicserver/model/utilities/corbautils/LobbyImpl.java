package org.amalgam.lexilogicserver.model.utilities.corbautils;

import org.amalgam.Utils.ObjectExceptions.*;
import org.amalgam.Utils.Objects.LobbyPOA;

public class LobbyImpl extends LobbyPOA {
    private String createdBy;
    private int lobbyID;
    private String winner;

    public LobbyImpl (){
       this.createdBy = "";
       this.lobbyID = 0;
       this.winner = "";
    }

    public LobbyImpl(int lobbyID, String createdBy, String winner) {
        super();
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
    public String createdBy() {
        return this.createdBy;
    }

    @Override
    public void createdBy(String newCreatedBy) {
        this.createdBy = newCreatedBy;
    }

    @Override
    public String winner() {
        return this.winner;
    }

    @Override
    public void winner(String newWinner) {
        this.winner = newWinner;
    }

    @Override
    public void updateWinner(String winnerName) throws InvalidNameException, PlayerDoesNotExistException, SQLError {
        this.winner = winnerName;
    }

    // TODO:Discuss with team
    @Override
    public void closeLobby() throws LobbyDoesNotExistException, CloseLobbyException, SQLError {

    }

    @Override
    public int getLobbyID() throws LobbyIDRetrievalException {
        return this.lobbyID;
    }

    @Override
    public String getCreatedBy() throws CreatedByRetrievalException {
        return this.createdBy;
    }
}
