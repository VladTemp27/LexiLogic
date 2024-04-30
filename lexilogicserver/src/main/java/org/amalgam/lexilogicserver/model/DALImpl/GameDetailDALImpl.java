package org.amalgam.lexilogicserver.model.DALImpl;

import org.amalgam.DAL.DALGameDetail.GameDetailsDALPOA;
import org.amalgam.DAL.DALGameDetail.GameDetailsDALPackage.GameDetail;
import org.amalgam.DAL.SQLExceptions.SQLCreateError;
import org.amalgam.DAL.SQLExceptions.SQLRetrieveError;
import org.amalgam.lexilogicserver.model.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameDetailDALImpl extends GameDetailsDALPOA {
    @Override
    public void insertNewGameDetail(int playerID, int lobbyID, int totalPoints) throws SQLCreateError {
        try (Connection conn = DatabaseUtil.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO gamedetails (playerID, lobbyID, totalPoints) VALUE (?, ?, ?)");
            stmt.setInt(1, playerID);
            stmt.setInt(2, lobbyID);
            stmt.setInt(3, totalPoints);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) System.out.println("INSERT NEW GAME DETAIL SUCCESS");
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

    @Override
    public GameDetail getGameDetailByID(int lobbyID) throws SQLRetrieveError {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM gamedetails WHERE lobbyID = ?");
            stmt.setInt(1, lobbyID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new GameDetail(rs.getInt("playerID"), rs.getInt("lobbyID"), rs.getInt("totalPoints"));
                } else {
                    return null;
                }
            }
		} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
