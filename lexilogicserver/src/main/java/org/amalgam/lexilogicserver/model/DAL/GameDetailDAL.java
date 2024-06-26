package org.amalgam.lexilogicserver.model.DAL;

import org.amalgam.lexilogicserver.model.DatabaseUtil;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.GameDetail;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.Player;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.PlayerGameDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class GameDetailDAL {

    public static void insertNewGameDetail(String username, int lobbyID, int totalPoints) {
        try (Connection conn = DatabaseUtil.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO gamedetails (playerID, lobbyID, totalPoints) SELECT playerID, ?, ? FROM player WHERE name = ?");
            stmt.setInt(1, lobbyID);
            stmt.setInt(2, totalPoints);
            stmt.setString(3, username);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("INSERT NEW GAME DETAIL SUCCESS");
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    public GameDetail getGameDetailByID(int lobbyID) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM gamedetails WHERE lobbyID = ?");
            stmt.setInt(1, lobbyID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Player player = PlayerDAL.getPlayerByID(rs.getInt("playerID"));
                    return new GameDetail(player.getUsername(), rs.getInt("lobbyID"), rs.getInt("totalPoints"));
                } else {
                    return null;
                }
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static LinkedList<GameDetail> getGameDetailByPID(int playerID) {
        LinkedList<GameDetail> listOfGameDetail = new LinkedList<>();
        try (Connection conn = DatabaseUtil.getConnection()) {
            String query = "SELECT gd.playerID, gd.lobbyID, gd.totalPoints, p.name " +
                           "FROM gamedetails gd "+
                           "JOIN player p USING(playerID) "+
                           "WHERE playerID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, playerID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String username = rs.getString("name");
                    int lobbyID = rs.getInt("lobbyID");
                    int totalPoints = rs.getInt("totalPoints");
                    GameDetail detail =  new GameDetail(username, lobbyID, totalPoints);
                    listOfGameDetail.add(detail);
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }catch(SQLException e ){
            if(!e.getMessage().equals("Operation not allowed after ResultSet closed")){
                e.printStackTrace();
            }
        }
        return listOfGameDetail;
    }

    public static void insertGameDetailFromPlayerDetail(PlayerGameDetail playerDetail, int lobbyID){
        try(Connection conn = DatabaseUtil.getConnection()){
            PreparedStatement statement = conn.prepareStatement("INSERT INTO gamedetails (playerID, lobbyID, " +
                    "totalPoints) VALUES ((SELECT playerID from player WHERE name = ?), ?, ?)");

            statement.setString(1, playerDetail.getUsername());
            statement.setInt(2, lobbyID);
            statement.setInt(3, playerDetail.getPoints());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("INSERT NEW GAME DETAIL SUCCESS");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
