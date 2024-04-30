package org.amalgam.lexilogicserver.model.DALImpl;

import org.amalgam.DAL.DALLobby.LobbyDALPOA;
import org.amalgam.DAL.DALLobby.LobbyDALPackage.Lobby;
import org.amalgam.DAL.SQLExceptions.SQLCreateError;
import org.amalgam.DAL.SQLExceptions.SQLRetrieveError;
import org.amalgam.lexilogicserver.model.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LobbyDALImpl extends LobbyDALPOA {
    @Override
    public void insertNewLobby(String createdBy, String winner) throws SQLCreateError {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO lobby (createdBy, winner) VALUE (?, ?)");
            stmt.setString(1, createdBy);
            stmt.setString(2, winner);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) System.out.println("INSERT NEW LOBBY SUCCESS");
		} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

    @Override
    public Lobby getLobbyByID(int lobbyId) throws SQLRetrieveError {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM lobby WHERE lobbyID = ?");
            stmt.setInt(1, lobbyId);
            try (ResultSet rs = stmt.executeQuery()) {
                return new Lobby(rs.getInt("lobbyID"), rs.getString("createdBy"), rs.getString("winner"));
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
