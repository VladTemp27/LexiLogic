package org.amalgam.lexilogicserver.model.utilities.referenceobjects;

public class LeaderBoard {
    private int leaderBoardID;
    private String username;
    private int points;
    private int rank;

    public LeaderBoard() {
    }

    public LeaderBoard(int leaderBoardID, String username, int points,int rank) {
        this.leaderBoardID = leaderBoardID;
        this.username = username;
        this.points = points;
        this.rank = rank;
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
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
