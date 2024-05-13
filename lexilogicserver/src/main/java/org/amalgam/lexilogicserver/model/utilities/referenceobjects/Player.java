package org.amalgam.lexilogicserver.model.utilities.referenceobjects;

public class Player {
    private int userID;
    private String username;
    private String password;
    private String lastLoggedIn;

    public Player(int userID, String username, String password, String lastLoggedIn) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.lastLoggedIn = lastLoggedIn;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastLoggedIn() {
        return lastLoggedIn;
    }

    public void setLastLoggedIn(String lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }
}
