package org.amalgam.lexilogicserver.model.DALImpl;

import org.amalgam.DAL.DALLeaderBoard.LeaderboardDALPOA;
import org.amalgam.DAL.DALLeaderBoard.LeaderboardDALPackage.Leaderboard;
import org.amalgam.DAL.SQLExceptions.SQLCreateError;
import org.amalgam.DAL.SQLExceptions.SQLRetrieveError;
import org.amalgam.lexilogicserver.model.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LeaderBoardDALImpl extends LeaderboardDALPOA {
    @Override
    public void insertNewLeaderboard(int userID, int totalPoints) throws SQLCreateError {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO leaderboards (userID, totalPoints) VALUE (?, ?)");
            stmt.setInt(1, userID);
            stmt.setInt(2, totalPoints);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) System.out.println("INSERT NEW LEADERBOARD SUCCESS");
		} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

    // TODO: a case where latest leaderboard is fetch
    @Override
    public Leaderboard getLeaderboardByID(int leaderboardID) throws SQLRetrieveError {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM leaderboards WHERE leaderBoardID = ?");
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Leaderboard(rs.getInt("leaderBoardID"), rs.getInt("userID"), rs.getInt("totalPoints"));
                } else {
                    return null;
                }
            }
		} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
