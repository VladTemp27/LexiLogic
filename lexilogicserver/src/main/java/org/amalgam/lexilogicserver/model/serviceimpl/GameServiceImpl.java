package org.amalgam.lexilogicserver.model.serviceimpl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.amalgam.Service.GameServiceModule.GameServicePOA;
import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.Utils.Exceptions.*;
import org.amalgam.Utils.Exceptions.DuplicateWordException;
import org.amalgam.lexilogicserver.model.DAL.LeaderBoardDAL;
import org.amalgam.lexilogicserver.model.microservices.Matchmaking.MatchmakingService;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.LeaderBoard;

import java.io.FileNotFoundException;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.PlayerGameDetail;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

import org.amalgam.lexilogicserver.model.handler.GameHandler.GameRoom;

public class GameServiceImpl extends GameServicePOA {
    private final MatchmakingService matchmakingService = new MatchmakingService();
    private final ConcurrentHashMap<String, PlayerCallback> playerCallbackMap = new ConcurrentHashMap<>();
    private final List<GameRoom> rooms = new LinkedList<>();
    private final Semaphore matchmakingLock = new Semaphore(1);

    /**
     * Matches players for a game. Waits up to 10 seconds for another player to join.
     *
     * @param playerCallback The player callback object.
     * @return A JSON string containing the game room response.
     */
    public String matchMake(PlayerCallback playerCallback) {
        PlayerGameDetail playerDetail = new PlayerGameDetail(playerCallback.username());
        matchmakingService.addToQueue(playerDetail);
        playerCallbackMap.put(playerCallback.username(), playerCallback);

        matchmakingService.startTimer();

        try {
            matchmakingLock.acquire();
            while (!matchmakingService.isTimerDone()) {
                LinkedList<PlayerGameDetail> players = matchmakingService.checkAndMatchPlayers();
                if (players != null) {
                    int roomID = rooms.size();
                    LinkedHashMap<String, PlayerGameDetail> playerDetailsMap = new LinkedHashMap<>();
                    LinkedHashMap<String, PlayerCallback> playerCallbacksMap = new LinkedHashMap<>();

                    for (PlayerGameDetail player : players) {
                        playerDetailsMap.put(player.getUsername(), player);
                        PlayerCallback callback = playerCallbackMap.get(player.getUsername());
                        if (callback != null) {
                            playerCallbacksMap.put(player.getUsername(), callback);
                        }
                    }

                    GameRoom gameRoom;
                    try {
                        gameRoom = new GameRoom(roomID, playerDetailsMap, playerCallbacksMap, 10);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    rooms.add(gameRoom);
                    return createGameRoomResponse(roomID, players);
                }
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            matchmakingLock.release();
        }

        return "{\"status\": \"timeout\", \"message\": \"No match found within 10 seconds.\"}";
    }

    private String createGameRoomResponse(int roomID, LinkedList<PlayerGameDetail> players) {
        // Implementation for creating a game room response JSON string.
        return "{\"roomID\": " + roomID + ", \"players\": " + players.toString() + "}";
    }

    @Override
    public char[][] fetchWordBox(int roomID) throws WordFetchFailedException, InvalidRoomIDException {
        return new char[0][];
    }

    @Override
    public String getLeaderboards() throws EmptyLeaderBoardException {
        List<LeaderBoard> leaderboards = LeaderBoardDAL.fetchLeaderBoards();

        if (leaderboards.isEmpty()) {
            throw new EmptyLeaderBoardException("Leaderboard is empty");
        }

        JsonObject rootObject = new JsonObject();
        rootObject.addProperty("object", "leaderboard");

        JsonArray leaderboardArray = new JsonArray();
        for (LeaderBoard leaderboard : leaderboards) {
            JsonObject leaderboardObj = new JsonObject();
            leaderboardObj.addProperty("username", leaderboard.getUsername());
            leaderboardObj.addProperty("pts", leaderboard.getPoints());
            leaderboardObj.addProperty("rank", leaderboard.getRank());
            leaderboardArray.add(leaderboardObj);
        }

        rootObject.add("leaderboard", leaderboardArray);

        return rootObject.toString();
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
