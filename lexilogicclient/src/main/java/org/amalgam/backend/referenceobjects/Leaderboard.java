package org.amalgam.backend.referenceobjects;

public class Leaderboard {
    private int leaderBoardID;
    private int playerRank;
    private String username;
    private int points;

    public Leaderboard() {}

    public Leaderboard(int leaderBoardID, int playerRank, String username, int points) {
        this.leaderBoardID = leaderBoardID;
        this.playerRank = playerRank;
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
