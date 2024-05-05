package org.amalgam.lexilogicserver.model.serviceimpl;

import org.amalgam.Service.GameExceptions.*;
import org.amalgam.Service.GameServiceModule.GameServicePOA;
import org.amalgam.Utils.Objects.Leaderboard;
import org.amalgam.Utils.Objects.Lobby;
import org.amalgam.lexilogicserver.model.utilities.corbautils.GameRoomImpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class GameServiceImpl extends GameServicePOA {
    ArrayList<GameRoomImpl> gameSessions = new ArrayList<>();
    LinkedHashMap<Integer, char[][]> wordboxes = new LinkedHashMap<>();

    @Override
    public char[][] fetchWordBox(int roomID) throws WordFetchException, InvalidRoomID {
        char[][] wordbox;
        if (wordboxes.containsKey(roomID)) {
            throw new InvalidRoomID("There is no room with the id " + roomID);
        }

        wordbox = wordboxes.get(roomID);
        if(wordbox == null){
           throw new WordFetchException("Room doesn't have a generated wordbox yet");
        }

        return wordbox;
    }

    @Override
    public Leaderboard[] getLeaderboards() throws EmptyLeaderBoard {
        //TODO: to use DAL to query the Leaderboard
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
    public String fetchWinner(int lobbyId) throws LobbyDoesNotExist, WinnerDoesNotExist {
        return "";
    }

    @Override
    public void insertLobby(Lobby lobby) throws InvalidLobbyData {

    }


}
