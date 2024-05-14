package org.amalgam.lexilogicserver.model.DAL;

import org.amalgam.lexilogicserver.model.DatabaseUtil;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.LeaderBoard;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LeaderBoardDAL {

    public static void insertNewLeaderboard(int userID, int totalPoints) {
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

    public static List<LeaderBoard> fetchLeaderBoards() {
        List<LeaderBoard> leaderboards = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection()) {
            String query = "SELECT " +
                    "l.leaderBoardID, " +
                    "u.name, " +
                    "l.totalPoints, " +
                    "(SELECT COUNT(*) + 1 FROM leaderboards l2 WHERE l2.totalPoints > l.totalPoints) AS leaderBoardRank " +
                    "FROM " +
                    "leaderboards l " +
                    "JOIN " +
                    "player u ON l.userID = u.playerID " +
                    "ORDER BY " +
                    "l.totalPoints DESC";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int leaderboardID = rs.getInt("leaderBoardID");
                    String name = rs.getString("name");
                    int totalPoints = rs.getInt("totalPoints");
                    int leaderBoardRank = rs.getInt("leaderBoardRank");
                    LeaderBoard leaderBoard = new LeaderBoard(leaderboardID, name, totalPoints, leaderBoardRank);
                    leaderboards.add(leaderBoard);
                }
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return leaderboards;
    }
}
