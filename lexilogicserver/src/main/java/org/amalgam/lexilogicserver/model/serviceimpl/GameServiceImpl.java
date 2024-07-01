package org.amalgam.lexilogicserver.model.serviceimpl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.amalgam.Service.GameServiceModule.GameServicePOA;
import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.Utils.Exceptions.*;
import org.amalgam.Utils.Exceptions.DuplicateWordException;
import org.amalgam.lexilogicserver.model.DAL.LeaderBoardDAL;
import org.amalgam.lexilogicserver.model.helpers.MatchMakeCleaner;
import org.amalgam.lexilogicserver.model.microservices.Matchmaking.MatchmakingService;
import org.amalgam.lexilogicserver.model.DAL.LobbyDAL;
import org.amalgam.lexilogicserver.model.microservices.wordbox.Reader;
import org.amalgam.lexilogicserver.model.microservices.gamesettings.SettingsHandler;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.LeaderBoard;

import java.io.File;
import java.io.FileNotFoundException;

import org.amalgam.lexilogicserver.model.utilities.referenceobjects.PlayerGameDetail;

import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.amalgam.lexilogicserver.model.handler.GameHandler.GameRoom;

public class GameServiceImpl extends GameServicePOA {
    private final MatchmakingService matchmakingService = new MatchmakingService();

    private final ConcurrentHashMap<String, PlayerCallback> playerCallbackMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, PlayerGameDetail> playerGameDetailsMap = new ConcurrentHashMap<>();

    private final List<GameRoom> rooms = new LinkedList<>();
    private final Semaphore matchmakingLock = new Semaphore(1);
    private final Semaphore returnLock = new Semaphore(1);

    private final AtomicInteger roomID = new AtomicInteger(-1);
    private final AtomicBoolean roomCreationAllowed = new AtomicBoolean(true);
    private final AtomicBoolean roomCreated = new AtomicBoolean(false);
//    WaitingRoom waitingRoom = new WaitingRoom(); // todo: consider this approach

   private final AtomicReference<String> owner = new AtomicReference<>();

   private final ExecutorService cleanerExecutor = Executors.newCachedThreadPool();

   private LinkedList<String> dictionary = new LinkedList<>();

   public GameServiceImpl(LinkedList<String> dictionary){
       this.dictionary = dictionary;
   }


   public void resetMatchMake(){
       matchmakingLock.release();
       this.matchmakingService.clearQueue();
       this.playerCallbackMap.clear();
       this.roomCreationAllowed.set(true);
       this.roomCreated.set(false);
   }

    /**
     * Matches players for a game. Waits up to 10 seconds for another player to join.
     *
     * @param playerCallback The player callback object.
     * @return A JSON string containing the game room response.
     */
    public String matchMake(PlayerCallback playerCallback){
        String username = playerCallback.username();
        try {
            if (matchmakingLock.tryAcquire(0, TimeUnit.SECONDS)) {
                owner.set(username);
                MatchMakeCleaner cleaner = new MatchMakeCleaner(matchmakingLock,matchmakingService,roomCreationAllowed,
                        roomCreated, playerCallbackMap);

                cleanerExecutor.submit(cleaner);
                matchmakingLock.release();
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        addPlayerToQueue(playerCallback);
        System.out.println("Added "+username+" to the queue");
        while(true){
            if(matchmakingService.isTimerDone()) break;
        }
        try{
            matchmakingService.queueLock.acquire();
        }catch(Exception e){
            e.printStackTrace();
        }
        matchmakingService.queueLock.release();


        if(!matchmakingService.isRoomValid()){
            System.out.println("invalid room");
            matchmakingService.markAsSent(username);
            System.out.println("sent response to "+username);
            return "{\"status\": \"timeout\", \"message\": \"Timer Done\"}";
        }
        //If method invocation was done by owner it creates room
        if(matchmakingService.isRoomValid() && username.equals(owner.get())){
            System.out.println("Owner Creating room");
            try {
                createGameRoom(this.matchmakingService.checkAndMatchPlayers());
                System.out.println("GAME ROOM Created");
            }catch(Exception e ){
                e.printStackTrace();
            }
            roomCreated.set(true);
        }
        while(!roomCreated.get()){}
        if(matchmakingService.isRoomValid() && roomCreated.get()){    // Returns to child players
            matchmakingService.markAsSent(username);
            System.out.println("Sending success response to user: "+username);
            return "{\"status\": \"success\", \"message\": \"Matchmaking Successful!\",\"gameRoomID\":"+roomID.get() + ", \"gameTime\": " + SettingsHandler.getGameTime() + "}";
        }
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
        String username = playerCallback.username();
        PlayerGameDetail playerDetail = new PlayerGameDetail(username);
        matchmakingService.addToQueue(playerDetail);
        playerCallbackMap.put(username, playerCallback);
        playerGameDetailsMap.put(username, playerDetail);

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

        try {
            GameRoom gameRoom = new GameRoom(roomID, new LinkedHashMap<>(playerGameDetailsMap),
                    new LinkedHashMap<>(playerCallbackMap), SettingsHandler.getGameTime(),
                    players.size(), this.dictionary);
            System.out.println("GameRoom Created");

            rooms.add(gameRoom);
            System.out.println(gameRoom);
            roomCreated.set(true);
            return;
            //System.out.println("SENDING GAME ROOM DETAILS");
            //gameRoom.stagePlayers();

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
    public void verifyWord(String word, String username, int gameRoomID) throws InvalidWordFormatException, DuplicateWordException {
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

