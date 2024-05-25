package org.amalgam.backend.microservices.objectparser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.amalgam.backend.referenceobjects.GameDetail;
import org.amalgam.backend.referenceobjects.LeaderBoard;
import org.amalgam.backend.referenceobjects.Lobby;
import org.amalgam.backend.referenceobjects.Player;
import org.amalgam.client.leaderboards.LeaderboardsController;
import org.amalgam.client.login.LoginController;

import java.util.ArrayList;
import java.util.List;

public class JsonObjectParser {

    public static List<Object> processMultiple(String jsonStringArray) {
        JsonArray jsonArray = JsonParser.parseString(jsonStringArray).getAsJsonArray();
        List<Object> result = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            String objectType = jsonObject.get("object").getAsString();

            switch (objectType) {
                case "game-detail":
                    result.add(parseGameDetails(jsonObject.getAsJsonArray("game-detail")));
                    break;
                case "leaderboard":
                    result.add(parseLeaderBoards(jsonObject.getAsJsonArray("leaderboard")));
                    break;
                case "lobby":
                    result.add(parseLobbies(jsonObject.getAsJsonArray("lobby")));
                    break;
                case "player":
                    result.add(parsePlayers(jsonObject.getAsJsonArray("player")));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown object type: " + objectType);
            }
        }

        return result;
    }

    private static List<GameDetail> parseGameDetails(JsonArray gameDetailArray) {
        List<GameDetail> gameDetails = new ArrayList<>();
        for (int i = 0; i < gameDetailArray.size(); i++) {
            JsonObject gameDObject = gameDetailArray.get(i).getAsJsonObject();
            GameDetail gameDetail = new GameDetail();

            gameDetail.setUsername(gameDObject.get("playerName").getAsString());
            gameDetail.setLobbyID(gameDObject.get("lobbyID").getAsInt());
            gameDetail.setTotalPoints(gameDObject.get("totalPoints").getAsInt());
            gameDetails.add(gameDetail);
        }
        return gameDetails;
    }

    private static List<LeaderBoard> parseLeaderBoards(JsonArray leaderboardArray) {
        List<LeaderBoard> leaderBoards = new ArrayList<>();
        for (int i = 0; i < leaderboardArray.size(); i++) {
            JsonObject LBObject = leaderboardArray.get(i).getAsJsonObject();
            LeaderBoard leaderBoard = new LeaderBoard();

            leaderBoard.setUsername(LBObject.get("username").getAsString());
            leaderBoard.setPoints(LBObject.get("pts").getAsInt());
            leaderBoard.setPlayerRank(LBObject.get("rank").getAsInt());
            leaderBoards.add(leaderBoard);
        }
        return leaderBoards;
    }

    private static List<Lobby> parseLobbies(JsonArray lobbyArray) {
        List<Lobby> lobbies = new ArrayList<>();
        for (int i = 0; i < lobbyArray.size(); i++) {
            JsonObject lobbyObject = lobbyArray.get(i).getAsJsonObject();
            Lobby lobby = new Lobby();

            lobby.setLobbyID(lobbyObject.get("lobbyID").getAsInt());
            lobby.setCreatedBy(lobbyObject.get("createdBy").getAsString());
            lobby.setWinner(lobbyObject.get("winner").getAsString());
            lobbies.add(lobby);
        }
        return lobbies;
    }

    private static List<Player> parsePlayers(JsonArray playerArray) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < playerArray.size(); i++) {
            JsonObject playerObject = playerArray.get(i).getAsJsonObject();
            Player player = new Player();

            player.setUserID(playerObject.get("playerID").getAsInt());
            player.setUsername(playerObject.get("name").getAsString());
            player.setLastLoggedIn(playerObject.get("lastLogin").getAsString());
            players.add(player);
        }
        return players;
    }

    public static List<LeaderboardsController.LeaderboardsData> parseLeaderboardsData(String leaderboards) {
        List<LeaderboardsController.LeaderboardsData> leaderboardsDataList = new ArrayList<>();
        JsonElement jsonElement = JsonParser.parseString(leaderboards);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("leaderboard");
        for (JsonElement element : jsonArray) {
            JsonObject object = element.getAsJsonObject();
            JsonElement username = object.get("username");
            JsonElement points = object.get("pts");
            JsonElement rank = object.get("rank");
            leaderboardsDataList.add(new LeaderboardsController.LeaderboardsData(rank.getAsString(), username.getAsString(),
                    points.getAsInt()));
        }
        return leaderboardsDataList;
    }
}
