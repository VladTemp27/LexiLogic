package org.amalgam.backend.microservices.objectparser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.amalgam.backend.referenceobjects.Leaderboard;
import org.amalgam.client.leaderboards.LeaderboardsController;

import java.util.ArrayList;
import java.util.List;

public class JsonObjectParser {
    public static List<Leaderboard> parseLeaderBoards(String jsonLeaderboardString) {
        List<Leaderboard> leaderboards = new ArrayList<>();
        JsonElement jsonElement = JsonParser.parseString(jsonLeaderboardString);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonLeaderboardArray = jsonObject.get("leaderboard").getAsJsonArray();
        for (int i = 0; i < jsonLeaderboardArray.size(); i++) {
            JsonObject LBObject = jsonLeaderboardArray.get(i).getAsJsonObject();
            Leaderboard leaderBoard = new Leaderboard();

            leaderBoard.setUsername(LBObject.get("username").getAsString());
            leaderBoard.setPoints(LBObject.get("pts").getAsInt());
            leaderBoard.setPlayerRank(LBObject.get("rank").getAsInt());
            leaderboards.add(leaderBoard);
        }
        return leaderboards;
    }

    public static String parseMatchMaking(String jsonMatchMaking, String key){
        JsonElement jsonElement = JsonParser.parseString(jsonMatchMaking);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (key.equals("status")) {
            return jsonObject.get(key).getAsString();
        }
        if (key.equals("gameRoomID")) {
            return jsonObject.get(key).getAsString();
        }
        return null;
    }

}
