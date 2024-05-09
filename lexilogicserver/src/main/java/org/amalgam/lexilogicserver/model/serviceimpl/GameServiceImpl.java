package org.amalgam.lexilogicserver.model.serviceimpl;

import org.amalgam.Service.GameExceptions.*;
import org.amalgam.Service.GameServiceModule.GameServicePOA;
import org.amalgam.Utils.Exceptions.*;
import org.amalgam.Utils.Exceptions.DuplicateWordException;
import org.amalgam.Utils.Objects.GameRoom;
import org.amalgam.Utils.Objects.Leaderboard;
import org.amalgam.Utils.Objects.Lobby;
import org.amalgam.Utils.UIControllers.PlayerCallback;

public class GameServiceImpl extends GameServicePOA {
    @Override
    public GameRoom matchMake(PlayerCallback player_callback) throws MatchCreationFailedException {
        return null;
    }

    @Override
    public char[][] fetchWordBox(int roomID) throws WordFetchFailedException, InvalidRoomIDException {
        return new char[0][];
    }

    @Override
    public Leaderboard[] getLeaderboards() throws EmptyLeaderBoardException {
        return new Leaderboard[0];
    }

    @Override
    public void verifyWord(String word) throws InvalidWordFormatException, DuplicateWordException {

    }

    @Override
    public int validateTotalPoints(String[] word) throws InsufficientWordPointsException, InvalidTotalPointsException {
        return 0;
    }

    @Override
    public String fetchWinner(int lobbyId) throws LobbyDoesNotExistException, WinnerDoesNotExistException {
        return "";
    }
}
