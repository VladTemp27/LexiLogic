package org.amalgam.backend.referenceobjects;

public class Lobby {
    private int lobbyID;
    private String createdBy;
    private String winner;

    public Lobby(int lobbyID, String createdBy, String winner) {
        this.lobbyID = lobbyID;
        this.createdBy = createdBy;
        this.winner = winner;
    }

    public Lobby() {
        this.lobbyID = 0;
        this.createdBy = null;
        this.winner = null;
    }

    public int getLobbyID() {
        return lobbyID;
    }

    public void setLobbyID(int lobbyID) {
        this.lobbyID = lobbyID;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
