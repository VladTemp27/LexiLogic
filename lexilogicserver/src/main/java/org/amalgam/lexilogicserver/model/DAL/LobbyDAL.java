package org.amalgam.lexilogicserver.model.DAL;

import org.amalgam.lexilogicserver.model.DatabaseUtil;
import org.amalgam.lexilogicserver.model.utilities.corbautils.LobbyImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LobbyDAL {
    public void insertNewLobby(int lobbyId, String createdBy, String winner) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO lobby (createdBy, winner) VALUES (?, ?)");
            stmt.setString(1, createdBy);
            stmt.setString(2, winner);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("INSERT NEW LOBBY SUCCESS");
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public LobbyImpl getLobbyByID(int lobbyId) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM lobby WHERE lobbyID = ?");
            stmt.setInt(1, lobbyId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new LobbyImpl(rs.getInt("lobbyID"), rs.getString("createdBy"), rs.getString("winner"));
                } else {
                    return null;
                }
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
