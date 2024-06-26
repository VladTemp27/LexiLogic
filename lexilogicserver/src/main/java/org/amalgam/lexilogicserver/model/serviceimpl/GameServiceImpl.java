package org.amalgam.lexilogicserver.model.serviceimpl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.amalgam.Service.GameServiceModule.GameServicePOA;
import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.Utils.Exceptions.*;
import org.amalgam.Utils.Exceptions.DuplicateWordException;
import org.amalgam.lexilogicserver.model.DAL.LeaderBoardDAL;
import org.amalgam.lexilogicserver.model.microservices.Matchmaking.MatchmakingService;
import org.amalgam.lexilogicserver.model.DAL.LobbyDAL;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.LeaderBoard;
import java.io.FileNotFoundException;

import org.amalgam.lexilogicserver.model.utilities.referenceobjects.PlayerGameDetail;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.amalgam.lexilogicserver.model.handler.GameHandler.GameRoom;

public class GameServiceImpl extends GameServicePOA {
    private final MatchmakingService matchmakingService = new MatchmakingService();
    private final ConcurrentHashMap<String, PlayerCallback> playerCallbackMap = new ConcurrentHashMap<>();
    private final List<GameRoom> rooms = new LinkedList<>();
    private final Semaphore matchmakingLock = new Semaphore(1);

    private final AtomicInteger roomID = new AtomicInteger(-1);

   private final AtomicBoolean roomCreationAllowed = new AtomicBoolean(true);
   private final AtomicBoolean roomCreated = new AtomicBoolean(false);


    /**
     * Matches players for a game. Waits up to 10 seconds for another player to join.
     *
     * @param playerCallback The player callback object.
     * @return A JSON string containing the game room response.
     */
    public String matchMake(PlayerCallback playerCallback) {
        addPlayerToQueue(playerCallback);

        System.out.println(playerCallback.username()+" entered matchmake");
        try {
            matchmakingLock.acquire(); // Locks the following code below, to prevent deadlock
            while (true) { // Change loop condition to always true
                //matchPlayers();
                if (matchmakingService.isTimerDone()) { //condition to check if timer is done
                    System.out.println("Matchmake Timer done");
                    if(matchmakingService.isRoomValid()){   // Checks if room is valid using atomic boolean
                        createGameRoom(this.matchmakingService.checkAndMatchPlayers()); // Creates a room but does
                        // not stage
                    }
                    break;  // Break out of the loop
                }
                Thread.sleep(100); //!keep this shall we revert to not using roomCreated variable and sentResponse!
            }
            matchmakingLock.release(); // Releases the locked code so other clients can execute it
            do{
                if(!matchmakingService.isRoomValid()) return "{\"status\": \"timeout\", \"message\": \"Timer Done\"}";
            }while(!roomCreated.get());
            if(matchmakingService.isRoomValid()){   // if room is valid sends response to user
                try {
                    // this returns success status as well as gameRoomID
                    // parse gameRoomID in client side to specify where to make the handshake call the readyHandshake
                    // request
                    System.out.println("RETURNGING GAME ROOM: "+roomID.get());

                    return "{\"status\": \"success\", \"message\": \"Matchmaking Successful!\",\"gameRoomID\":"+roomID.get()+"}";
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted Thread");
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("Executing finally block for user "+playerCallback.username());
            System.out.println("Clearing maps and services");

            this.matchmakingService.clearQueue();
            this.playerCallbackMap.clear();
            this.roomCreationAllowed.set(true);
            this.roomCreated.set(false);
        }
        //returns if matchmake has failed
        return "{\"status\": \"timeout\", \"message\": \"Timer Done\"}";
    }



    /**
     * Method to invoke when to handshake with the object of Game Room
     * @param username      username of the user (String)
     * @param gameroomID    id of the corresponding game room (int) this is given at the response body of matchmake
     */
    @Override
    public void readyHandshake(String username, int gameroomID) {
        System.out.printf(username+" Triggered Ready Response");
        for(GameRoom room : rooms){
            if(room.getRoomID() == gameroomID){
                room.markPlayerReadyToReceive(username);
            }
        }
    }

    /**
     * Adds the player to the matchmaking queue and starts the timer.
     *
     * @param playerCallback The player callback object.
     */
    private void addPlayerToQueue(PlayerCallback playerCallback) {
        PlayerGameDetail playerDetail = new PlayerGameDetail(playerCallback.username());
        matchmakingService.addToQueue(playerDetail);
        playerCallbackMap.put(playerCallback.username(), playerCallback);
    }

    /**
     * Matches players and creates a game room if enough players are found.
     */
    private void matchPlayers(){
        LinkedList<PlayerGameDetail> players = null;
        try {
            players = matchmakingService.checkAndMatchPlayers();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (players != null && players.size() >= 2 && matchmakingService.isTimerDone()) { // Check if enough players are matched
            System.out.println("Creating GameRoom");
            createGameRoom(players);
            //roomValid = true;
        } else {
        }
    }

    /**
     * Creates a game room with the matched players.
     *
     * @param players The list of matched players.
     */
    private void createGameRoom(LinkedList<PlayerGameDetail> players) {
        if(!roomCreationAllowed.get()){
            System.out.println("Game Creation Not Allowed");
            return;
        }
        roomCreationAllowed.set(false);
        System.out.println("Creating room");
        int roomID = this.roomID.get();
        roomID++;
        this.roomID.set(roomID);
        System.out.println("Room ID: "+roomID);
        LinkedHashMap<String, PlayerGameDetail> playerDetailsMap = new LinkedHashMap<>();
        LinkedHashMap<String, PlayerCallback> playerCallbacksMap = new LinkedHashMap<>();

        for (PlayerGameDetail player : players) {
            playerDetailsMap.put(player.getUsername(), player);
            PlayerCallback callback = playerCallbackMap.get(player.getUsername());
            if (callback != null) {
                playerCallbacksMap.put(player.getUsername(), callback);
            }
        }

        try {
            GameRoom gameRoom = new GameRoom(roomID, playerDetailsMap, playerCallbacksMap, 30, players.size());
            System.out.println("GameRoom Created");
            if (matchmakingService.isTimerDone()) {
                rooms.add(gameRoom);
                System.out.println(gameRoom);
                roomCreated.set(true);
                //System.out.println("SENDING GAME ROOM DETAILS");
                //gameRoom.stagePlayers();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
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
    public synchronized void verifyWord(String word, String username, int gameRoomID) throws InvalidWordFormatException, DuplicateWordException {
        int tempIndex = getRoomIndexFromID(gameRoomID);
        System.out.println(username+" submitted word: "+word);
//        GameRoom temp = rooms.get(tempIndex);
//
//        temp.submitWord(word, username);
//
//        rooms.remove(tempIndex);
//        rooms.add(temp);
       rooms.get(tempIndex).submitWord(word, username);
    }

    @Deprecated
    @Override
    public int validateTotalPoints() throws InsufficientWordPointsException, InvalidTotalPointsException {
        return 0;
    }

    @Override
    public String fetchWinner(int lobbyId) throws LobbyDoesNotExistException, WinnerDoesNotExistException {
        return LobbyDAL.getWinnerByLobbyID(lobbyId);
    }

    //TODO test if synchronized makes it buggy
    @Override
    public String playerReady(String username, int gameRoomID) {
        int origIndex = getRoomIndexFromID(gameRoomID);
        GameRoom temp = rooms.get(origIndex);

        temp.setPlayerReady(username);

        rooms.remove(origIndex);
        rooms.add(temp);
        return "";
    }

    private int getRoomIndexFromID(int gameRoomID) {
        for (GameRoom room : rooms) {
            if (gameRoomID == room.getRoomID()) {
                return rooms.indexOf(room);
            }
        }
        return -1;
    }
}

