package org.amalgam.lexilogicserver.model.DAL;

import org.amalgam.lexilogicserver.model.DatabaseUtil;
import org.amalgam.lexilogicserver.model.utilities.corbautils.LeaderBoardImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LeaderBoardDAL {

    public void insertNewLeaderboard(int userID, int totalPoints) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO leaderboards (userID, totalPoints) VALUES (?, ?)");
            stmt.setInt(1, userID);
            stmt.setInt(2, totalPoints);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("INSERT NEW LEADERBOARD SUCCESS");
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public LeaderBoardImpl getLeaderboardByID(int leaderboardID) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM leaderboards WHERE leaderBoardID = ?");
            stmt.setInt(1, leaderboardID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new LeaderBoardImpl(rs.getInt("leaderBoardID"), rs.getInt("userID"), rs.getInt("totalPoints"));
                } else {
                    return null;
                }
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
