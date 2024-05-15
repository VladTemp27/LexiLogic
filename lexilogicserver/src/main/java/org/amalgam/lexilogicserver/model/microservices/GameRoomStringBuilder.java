package org.amalgam.lexilogicserver.model.microservices;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.GameRoom;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.PlayerGameDetail;

public class GameRoomStringBuilder {

    public static String buildGameRoomJSON(GameRoom gameRoom) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("roomID", gameRoom.getRoomID());
        jsonObject.addProperty("gameDone", gameRoom.isGameDone());

        JsonArray detailsArray = new JsonArray();
        for (PlayerGameDetail detail : gameRoom.getDetails()) {
            detailsArray.add(buildPlayerGameDetailJSON(detail));
        }
        jsonObject.add("details", detailsArray);

        return jsonObject.toString();
    }

    private static JsonObject buildPlayerGameDetailJSON(PlayerGameDetail playerGameDetail) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", playerGameDetail.getUsername());
        jsonObject.addProperty("points", playerGameDetail.getPoints());

        JsonArray wordsArray = new JsonArray();
        for (String word : playerGameDetail.getWords()) {
            wordsArray.add(word);
        }
        jsonObject.add("words", wordsArray);

        JsonArray dupedWordsArray = new JsonArray();
        for (String dupedWord : playerGameDetail.getDupedWords()) {
            dupedWordsArray.add(dupedWord);
        }
        jsonObject.add("dupedWords", dupedWordsArray);

        return jsonObject;
    }
}
