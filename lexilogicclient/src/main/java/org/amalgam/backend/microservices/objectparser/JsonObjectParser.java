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
//    public static List<Object> processMultiple(String jsonStringArray) {
//        List<Object> result = new ArrayList<>();
//        for (int i = 0; i < jsonArray.size(); i++) {
//            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
//            String objectType = jsonObject.get("object").getAsString();
//
//            switch (objectType) {
//                case "game-detail":
//                    result.add(parseGameDetails(jsonObject.getAsJsonArray("game-detail")));
//                    break;
//                case "leaderboard":
//                    result.add(parseLeaderBoards(jsonObject.getAsJsonArray("leaderboard")));
//                    break;
//                case "lobby":
//                    result.add(parseLobbies(jsonObject.getAsJsonArray("lobby")));
//                    break;
//                case "player":
//                    result.add(parsePlayers(jsonObject.getAsJsonArray("player")));
//                    break;
//                default:
//                    throw new IllegalArgumentException("Unknown object type: " + objectType);
//            }
//        }
//        return result;
//    }

//    public static List<GameDetail> parseGameDetails(String jsonGameString) {
//        List<GameDetail> gameDetails = new ArrayList<>();
//        for (int i = 0; i < gameDetailArray.size(); i++) {
//            JsonObject gameDObject = gameDetailArray.get(i).getAsJsonObject();
//            GameDetail gameDetail = new GameDetail();
//
//            gameDetail.setUsername(gameDObject.get("playerName").getAsString());
//            gameDetail.setLobbyID(gameDObject.get("lobbyID").getAsInt());
//            gameDetail.setTotalPoints(gameDObject.get("totalPoints").getAsInt());
//            gameDetails.add(gameDetail);
//        }
//        return gameDetails;
//    }

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

//    public static List<Lobby> parseLobby(String jsonLobbyString) {
//        List<Lobby> lobbies = new ArrayList<>();
//        for (int i = 0; i < lobbyArray.size(); i++) {
//            JsonObject lobbyObject = lobbyArray.get(i).getAsJsonObject();
//            Lobby lobby = new Lobby();
//
//            lobby.setLobbyID(lobbyObject.get("lobbyID").getAsInt());
//            lobby.setCreatedBy(lobbyObject.get("createdBy").getAsString());
//            lobby.setWinner(lobbyObject.get("winner").getAsString());
//            lobbies.add(lobby);
//        }
//        return lobbies;
//    }

//    public static List<Player> parsePlayers(String jsonPlayersString) {
//        List<Player> players = new ArrayList<>();
//        for (int i = 0; i < playerArray.size(); i++) {
//            JsonObject playerObject = playerArray.get(i).getAsJsonObject();
//            Player player = new Player();
//
//            player.setUserID(playerObject.get("playerID").getAsInt());
//            player.setUsername(playerObject.get("name").getAsString());
//            player.setLastLoggedIn(playerObject.get("lastLogin").getAsString());
//            players.add(player);
//        }
//        return players;
//    }
}
