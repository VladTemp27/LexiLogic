package org.amalgam.lexilogicserver.model.DAL;

import org.amalgam.lexilogicserver.model.DatabaseUtil;
import org.amalgam.lexilogicserver.model.utilities.corbautils.GameDetailImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public GameDetailImpl getGameDetailByID(int lobbyID) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM gamedetails WHERE lobbyID = ?");
            stmt.setInt(1, lobbyID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new GameDetailImpl(rs.getInt("playerID"), rs.getInt("lobbyID"), rs.getInt("totalPoints"));
                } else {
                    return null;
                }
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
