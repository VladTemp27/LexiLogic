package org.amalgam.backend.referenceobjects;

public class LeaderBoard {
    private int leaderBoardID;
    private String username;
    private int points;

    private int playerRank;

    public LeaderBoard() {
    }

    public LeaderBoard(int leaderBoardID, String username, int points) {
        this.leaderBoardID = leaderBoardID;
        this.username = username;
        this.points = points;
    }

    public int getLeaderBoardID() {
        return leaderBoardID;
    }

    public void setLeaderBoardID(int leaderBoardID) {
        this.leaderBoardID = leaderBoardID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPlayerRank() {
        return playerRank;
    }

    public void setPlayerRank(int rank) {
        this.playerRank = rank;
    }
}
