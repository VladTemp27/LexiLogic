package org.amalgam.lexilogicserver.model.DAL;

import org.amalgam.lexilogicserver.model.DatabaseUtil;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.GameDetail;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class GameDetailDAL {

    public void insertNewGameDetail(int playerID, int lobbyID, int totalPoints) {
        try (Connection conn = DatabaseUtil.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO gamedetails (playerID, lobbyID, totalPoints) VALUES (?, ?, ?)");
            stmt.setInt(1, playerID);
            stmt.setInt(2, lobbyID);
            stmt.setInt(3, totalPoints);
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
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM gamedetails WHERE playerID = ?");
            stmt.setInt(1, playerID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Player player = PlayerDAL.getPlayerByID(rs.getInt("playerID"));
                    GameDetail detail =  new GameDetail(player.getUsername(), rs.getInt("lobbyID"), rs.getInt("totalPoints"));
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
}
