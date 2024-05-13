package org.amalgam.lexilogicserver.model.utilities.referenceobjects;

public class GameDetail {
    private String username;
    private int lobbyID;
    private int totalPoints;

    public GameDetail() {
    }

    public GameDetail(String username, int lobbyID, int totalPoints) {
        this.username = username;
        this.lobbyID = lobbyID;
        this.totalPoints = totalPoints;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLobbyID() {
        return lobbyID;
    }

    public void setLobbyID(int lobbyID) {
        this.lobbyID = lobbyID;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
}
