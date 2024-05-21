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
                System.out.println("Loop started");
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
                    gameRoom.stagePlayers();
//                    return createGameRoomResponse(roomID, players);
                }
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted Thread");
            Thread.currentThread().interrupt();
        } finally {
            matchmakingLock.release();
        }

        return "{\"status\": \"timeout\", \"message\": \"No match found within 10 seconds DEBUG MODE.\"}";
    }

    private String createGameRoomResponse(int roomID, LinkedList<PlayerGameDetail> players) {
        // Implementation for creating a game room response JSON string.
        return "{\"roomID\": " + roomID + ", \"players\": " + players.toString() + "}";
    }

    @Deprecated
    @Override
    public char[][] fetchWordBox(int roomID) throws WordFetchFailedException, InvalidRoomIDException {
        return new char[0][];
    }

    // TODO: why there is JSON
    @Override
    public String getLeaderboards() throws EmptyLeaderBoardException {
        List<LeaderBoard> leaderboards = LeaderBoardDAL.fetchLeaderBoards();

        if (leaderboards.isEmpty()) {
            throw new EmptyLeaderBoardException("Leaderboard is empty");
        }

//        JsonObject rootObject = new JsonObject();

        JsonArray leaderboardArray = new JsonArray();
        for (LeaderBoard leaderboard : leaderboards) {
            leaderboardArray.add(leaderboard.getUsername());
            leaderboardArray.add(leaderboard.getPoints());
            leaderboardArray.add(leaderboard.getRank());
//            JsonObject leaderboardObj = new JsonObject();
//            leaderboardObj.addProperty("username", leaderboard.getUsername());
//            leaderboardObj.addProperty("pts", leaderboard.getPoints());
//            leaderboardObj.addProperty("rank", leaderboard.getRank());
//            leaderboardArray.add(leaderboardObj);
        }

//        rootObject.add("leaderboard", leaderboardArray);

//        return rootObject.toString();
        return leaderboardArray.toString();
    }


    @Override
    public synchronized void verifyWord(String word,String username, int gameRoomID) throws InvalidWordFormatException, DuplicateWordException {
        int tempIndex = getRoomIndexFromID(gameRoomID);
        GameRoom temp = rooms.get(tempIndex);

        temp.submitWord(word, username);

        rooms.remove(tempIndex);
        rooms.add(temp);
    }

    @Deprecated
    @Override
    public int validateTotalPoints() throws InsufficientWordPointsException, InvalidTotalPointsException {
        return 0;
    }

    @Override
    public String fetchWinner(int lobbyId) throws LobbyDoesNotExistException, WinnerDoesNotExistException {
        return "";
    }

    @Override
    public synchronized String playerReady(String username, int gameRoomID) {
        int origIndex = getRoomIndexFromID(gameRoomID);
        GameRoom temp = rooms.get(origIndex);

        temp.setPlayerReady(username);

        rooms.remove(origIndex);
        rooms.add(temp);
        return null;
    }

    private int getRoomIndexFromID(int gameRoomID){
        for(GameRoom room : rooms){
            if(gameRoomID == room.getRoomID()){
                return rooms.indexOf(room);
            }
        }
        return -1;
    }
}
