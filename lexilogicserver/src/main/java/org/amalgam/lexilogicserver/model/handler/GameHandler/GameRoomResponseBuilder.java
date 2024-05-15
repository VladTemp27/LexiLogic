package org.amalgam.lexilogicserver.model.handler.GameHandler;

import org.amalgam.lexilogicserver.model.utilities.referenceobjects.PlayerGameDetail;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.LinkedHashMap;

public class GameRoomResponseBuilder {

    private static final Gson gson = new Gson();

    public static String buildGameStartedResponse(LinkedHashMap<String, PlayerGameDetail> details) {
        JsonObject response = new JsonObject();
        response.addProperty("state", "game_started");
        response.add("players", gson.toJsonTree(details));
        return response.toString();
    }

    public static String buildStagePlayersResponse(int countdown) {
        JsonObject response = new JsonObject();
        response.addProperty("state", "staging");
        response.addProperty("countdown", countdown);
        return response.toString();
    }


    public static String buildWinnerResponse(String winner) {
        JsonObject response = new JsonObject();
        response.addProperty("state", "game_done");
        response.addProperty("winner", winner);
        return gson.toJson(response);
    }


}
