package org.amalgam.lexilogicserver.model.handler.GameHandler;

import com.google.gson.JsonArray;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.GameDetail;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.PlayerGameDetail;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class GameRoomResponseBuilder {

    private static final Gson gson = new Gson();

    public static String buildGameStartedResponse(GameRoom gameRoom) {
        int roomID = gameRoom.getRoomID();
        char[][] charMatrix = gameRoom.getCharMatrix();
        int capacity = gameRoom.getCapacity();

        JsonObject response = new JsonObject();
        response.addProperty("state", "game_started");
        response.addProperty("room_id", roomID);
        response.addProperty("current_round", gameRoom.getCurrentRound());
        response.addProperty("seconds_round_duration", gameRoom.getSecondsRoundDuration());
        response.addProperty("round_done", gameRoom.isRoundDone());
        response.addProperty("capacity", capacity);

        JsonArray matrixArray = new JsonArray();
        for (char[] row : charMatrix) {
            JsonArray rowArray = new JsonArray();
            for (char c : row) {
                rowArray.add(String.valueOf(c));
            }
            matrixArray.add(rowArray);
        }
        response.add("char_matrix", matrixArray);

        return response.toString();
    }


    public static String buildStagePlayersResponse(GameRoom gameRoom, int countdown) {
        int roomID = gameRoom.getRoomID();
        LinkedHashMap<String, PlayerGameDetail> details = gameRoom.getDetails();
        char[][] charMatrix = gameRoom.getCharMatrix();

        JsonObject response = new JsonObject();
        response.addProperty("state", "staging");
        response.addProperty("countdown", countdown);
        response.addProperty("room_id", roomID);
        response.addProperty("current_round", gameRoom.getCurrentRound());
        response.addProperty("seconds_round_duration", gameRoom.getSecondsRoundDuration());
        response.addProperty("round_done", gameRoom.isRoundDone());

        JsonArray matrixArray = new JsonArray();
        for (char[] row : charMatrix) {
            JsonArray rowArray = new JsonArray();
            for (char c : row) {
                rowArray.add(String.valueOf(c));
            }
            matrixArray.add(rowArray);
        }
        response.add("char_matrix", matrixArray);

        JsonObject gameRoomJson = new JsonObject();
        for (Map.Entry<String, PlayerGameDetail> entry : details.entrySet()) {
            String username = entry.getKey();
            PlayerGameDetail playerDetail = entry.getValue();
            JsonObject playerInfo = new JsonObject();
            playerInfo.addProperty("username", username);
            playerInfo.addProperty("points", playerDetail.getPoints());
            playerInfo.addProperty("ready", playerDetail.isReady());

            JsonArray wordsArray = new JsonArray();
            playerDetail.getWords().forEach(wordsArray::add);
            playerInfo.add("words", wordsArray);

            JsonArray dupedWordsArray = new JsonArray();
            playerDetail.getDupedWords().forEach(dupedWordsArray::add);
            playerInfo.add("duped_words", dupedWordsArray);

            gameRoomJson.add(username, playerInfo);
        }

        JsonObject roundsJson = new JsonObject();
        for (Map.Entry<Integer, String> entry : gameRoom.getRounds().entrySet()) {
            int roundNumber = entry.getKey();
            String winner = entry.getValue();
            roundsJson.addProperty("round_" + roundNumber, winner);
        }
        gameRoomJson.add("rounds", roundsJson);

        response.add("game_room", gameRoomJson);

        return response.toString();
    }

        public static String buildInvalidWordResponse() {

        JsonObject response = new JsonObject();
        response.addProperty("state", "invalid_word");


        return response.toString();
    }

    public static String roundDoneResponse(GameRoom gameRoom) {
        int roomID = gameRoom.getRoomID();
        LinkedHashMap<String, PlayerGameDetail> details = gameRoom.getDetails();
        char[][] charMatrix = gameRoom.getCharMatrix();

        JsonObject response = new JsonObject();
        response.addProperty("state", "round_done");
        response.addProperty("room_id", roomID);
        response.addProperty("current_round", gameRoom.getCurrentRound());
        response.addProperty("seconds_round_duration", gameRoom.getSecondsRoundDuration());
        response.addProperty("round_done", gameRoom.isRoundDone());

        JsonArray matrixArray = new JsonArray();
        for (char[] row : charMatrix) {
            JsonArray rowArray = new JsonArray();
            for (char c : row) {
                rowArray.add(String.valueOf(c));
            }
            matrixArray.add(rowArray);
        }
        response.add("char_matrix", matrixArray);

        JsonObject gameRoomJson = new JsonObject();
        for (Map.Entry<String, PlayerGameDetail> entry : details.entrySet()) {
            String username = entry.getKey();
            PlayerGameDetail playerDetail = entry.getValue();
            JsonObject playerInfo = new JsonObject();
            playerInfo.addProperty("username", username);
            playerInfo.addProperty("points", playerDetail.getPoints());
            playerInfo.addProperty("ready", playerDetail.isReady());

            JsonArray wordsArray = new JsonArray();
            playerDetail.getWords().forEach(wordsArray::add);
            playerInfo.add("words", wordsArray);

            JsonArray dupedWordsArray = new JsonArray();
            playerDetail.getDupedWords().forEach(dupedWordsArray::add);
            playerInfo.add("duped_words", dupedWordsArray);

            gameRoomJson.add(username, playerInfo);
        }

        JsonObject roundsJson = new JsonObject();
        for (Map.Entry<Integer, String> entry : gameRoom.getRounds().entrySet()) {
            int roundNumber = entry.getKey();
            String winner = entry.getValue();
            roundsJson.addProperty("round_" + roundNumber, winner);
        }
        gameRoomJson.add("rounds", roundsJson);

        response.add("game_room", gameRoomJson);

        return response.toString();
    }


    public static String buildWinnerResponse(String winner) {
        JsonObject response = new JsonObject();
        response.addProperty("state", "game_done");
        response.addProperty("winner", winner);
        return gson.toJson(response);
    }

    public static String dupedWordResponseOwner (GameRoom gameRoom, String owner){
        LinkedHashMap<String, PlayerGameDetail> details = gameRoom.getDetails();
        JsonObject response = new JsonObject();
        response.addProperty("state", "duped");
        response.addProperty("message", "You've been duped!");

        JsonObject playerDetails = new JsonObject();
        int i=0;

        for (Map.Entry<String, PlayerGameDetail> entry : details.entrySet()) {
            String username = entry.getKey();
            PlayerGameDetail playerDetail = entry.getValue();
            JsonObject playerInfo = new JsonObject();
            playerInfo.addProperty("username", username);
            playerInfo.addProperty("points", playerDetail.getPoints());
            playerDetails.add("player_" + i, playerInfo);
            i++;
        }
        response.add("player_details", playerDetails);
        return response.toString();
    }

    public static String dupedWordResponseGeneric(){
        JsonObject response = new JsonObject();
        response.addProperty("state", "self_duplicate");
        response.addProperty("message", "You've already entered that word!");
        return response.toString();

    }

    public static String dupedWordResponseDuper (GameRoom gameRoom, String owner){
        LinkedHashMap<String, PlayerGameDetail> details = gameRoom.getDetails();
        JsonObject response = new JsonObject();
        response.addProperty("state", "duped_word");
        response.addProperty("message", "You've duped someone!");
        JsonObject playerDetails = new JsonObject();
        int i=0;

        for (Map.Entry<String, PlayerGameDetail> entry : details.entrySet()) {
            String username = entry.getKey();
            PlayerGameDetail playerDetail = entry.getValue();
            JsonObject playerInfo = new JsonObject();
            playerInfo.addProperty("username", username);
            playerInfo.addProperty("points", playerDetail.getPoints());
            playerDetails.add("player_" + i, playerInfo);
            i++;
        }
        response.add("player_details", playerDetails);
        return response.toString();
    }

    public static String buildPlayerScoreResponse(GameRoom gameRoom) {
        int capacity = gameRoom.getCapacity();
        LinkedHashMap<String, PlayerGameDetail> details = gameRoom.getDetails();
        JsonObject response = new JsonObject();
        response.addProperty("state", "game_room");
        response.addProperty("capacity", capacity);
        JsonObject gameRoomJson = new JsonObject();
        int i=0;
        for (Map.Entry<String, PlayerGameDetail> entry : details.entrySet()) {
            String username = entry.getKey();
            PlayerGameDetail playerDetail = entry.getValue();
            JsonObject playerInfo = new JsonObject();
            playerInfo.addProperty("username", username);
            playerInfo.addProperty("points", playerDetail.getPoints());
            playerInfo.addProperty("ready", playerDetail.isReady());
            JsonArray wordsArray = new JsonArray();
            playerDetail.getWords().forEach(wordsArray::add);
            playerInfo.add("words", wordsArray);
            JsonArray dupedWordsArray = new JsonArray();
            playerDetail.getDupedWords().forEach(dupedWordsArray::add);
            playerInfo.add("duped_words", dupedWordsArray);
            gameRoomJson.add("player_" + i, playerInfo);
            i++;
        }
//        JsonObject roundsJson = new JsonObject();
//        for (Map.Entry<Integer, String> entry : gameRoom.getRounds().entrySet()) {
//            int roundNumber = entry.getKey();
//            String winner = entry.getValue();
//            roundsJson.addProperty("round_" + roundNumber, winner);
//        }
//        gameRoomJson.add("rounds", roundsJson);

        response.add("game_room_property", gameRoomJson);

        return response.toString();
    }
}
