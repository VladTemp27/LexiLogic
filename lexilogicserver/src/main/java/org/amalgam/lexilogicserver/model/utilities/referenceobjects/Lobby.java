package org.amalgam.lexilogicserver.model.utilities.referenceobjects;

public class Lobby {
    private int lobbyID;
    private String winner;

    public Lobby(int lobbyID, String winner) {
        this.lobbyID = lobbyID;
        this.winner = winner;
    }

    public Lobby() {
        this.lobbyID = 0;
        this.winner = null;
    }

    public int getLobbyID() {
        return lobbyID;
    }

    public void setLobbyID(int lobbyID) {
        this.lobbyID = lobbyID;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
