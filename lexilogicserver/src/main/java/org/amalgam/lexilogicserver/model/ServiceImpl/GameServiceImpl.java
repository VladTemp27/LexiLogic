package org.amalgam.lexilogicserver.model.ServiceImpl;

import org.amalgam.Service.Game.GameServicePOA;
import org.amalgam.Service.Game.GameServicePackage.GameRoom;
import org.amalgam.Service.Game.GameServicePackage.Leaderboard;
import org.amalgam.Service.Game.GameServicePackage.Lobby;
import org.amalgam.Service.Game.PlayerCallback;
import org.amalgam.Service.GameExceptions.*;

public class GameServiceImpl extends GameServicePOA {
    @Override
    public GameRoom matchMake(PlayerCallback player_callback) throws MatchCreationFailed {
        return null;
    }

    @Override
    public char[][] fetchWordBox(int roomID) throws WordFetchException, InvalidRoomID {
        return new char[0][];
    }

    @Override
    public Leaderboard[] getLeaderboards() throws EmptyLeaderBoard {
        return new Leaderboard[0];
    }

    @Override
    public void verifyWord(String word) throws InvalidWordFormat, DuplicateWordException {

    }

    @Override
    public int validateTotalPoints(String[] word) throws InsufficientWordPoints, InvalidTotalPoints {
        return 0;
    }

    @Override
    public String fetchWinner(int lobbyId) throws InvalidLobbyID, WinnerDoesNotExist {
        return null;
    }

    @Override
    public void insertLobby(Lobby lobby) throws InvalidLobbyData {

    }
}
