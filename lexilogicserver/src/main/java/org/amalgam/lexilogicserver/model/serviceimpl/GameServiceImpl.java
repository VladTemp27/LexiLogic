package org.amalgam.lexilogicserver.model.serviceimpl;

import org.amalgam.Service.GameServiceModule.GameServicePOA;
import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.Utils.Exceptions.*;
import org.amalgam.Utils.Exceptions.DuplicateWordException;

public class GameServiceImpl extends GameServicePOA {

    @Override
    public String matchMake(PlayerCallback player_callback) throws MatchCreationFailedException {
        return "";
    }

    @Override
    public char[][] fetchWordBox(int roomID) throws WordFetchFailedException, InvalidRoomIDException {
        return new char[0][];
    }

    @Override
    public String getLeaderboards() throws EmptyLeaderBoardException {
        return "";
    }

    @Override
    public void verifyWord(String word) throws InvalidWordFormatException, DuplicateWordException {

    }

    @Override
    public int validateTotalPoints() throws InsufficientWordPointsException, InvalidTotalPointsException {
        return 0;
    }

    @Override
    public String fetchWinner(int lobbyId) throws LobbyDoesNotExistException, WinnerDoesNotExistException {
        return "";
    }
}
