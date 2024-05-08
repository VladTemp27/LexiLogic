package org.amalgam.lexilogicserver.model.serviceimpl;

import org.amalgam.Service.GameExceptions.*;
import org.amalgam.Service.GameServiceModule.GameServicePOA;
import org.amalgam.Utils.Objects.Leaderboard;
import org.amalgam.Utils.Objects.Lobby;

public class GameServiceImpl extends GameServicePOA {
    public char[][] fetchWordBox(int roomID) throws WordFetchException, InvalidRoomID {
        return new char[0][];
    }

    public Leaderboard[] getLeaderboards() throws EmptyLeaderBoard {
        return new Leaderboard[0];
    }

    public void verifyWord(String word) throws InvalidWordFormat, DuplicateWordException {

    }

    public int validateTotalPoints(String[] word) throws InsufficientWordPoints, InvalidTotalPoints {
        return 0;
    }

    public String fetchWinner(int lobbyId) throws LobbyDoesNotExist, WinnerDoesNotExist {
        return null;
    }

    public void insertLobby(Lobby lobby) throws InvalidLobbyData {

    }
}
