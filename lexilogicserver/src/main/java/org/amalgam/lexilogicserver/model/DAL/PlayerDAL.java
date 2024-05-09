package org.amalgam.lexilogicserver.model.DAL;

import org.amalgam.lexilogicserver.model.DatabaseUtil;
import org.amalgam.lexilogicserver.model.utilities.corbautils.PlayerImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDAL {
    public void insertNewPlayer(String username, String password) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO player (name, password) VALUES (?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            System.out.println("CREATE NEW PLAYER SUCCESS");
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public PlayerImpl getPlayerByID(int playerID) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM player WHERE playerID = ?");
            stmt.setInt(1, playerID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int fetchedPlayerID = rs.getInt("playerID");
                    String fetchedUsername = rs.getString("name");
                    String fetchedPassword = rs.getString("password");
                    String fetchedLastLogin = rs.getString("lastLogin");
                    return new PlayerImpl(fetchedPlayerID, fetchedUsername, fetchedPassword, fetchedLastLogin);
                } else {
                    return null;
                }
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePassword(int playerID, String newPassword) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE player SET password = ? WHERE playerID = ?");
            stmt.setString(1, newPassword);
            stmt.setInt(2, playerID);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("UPDATE PASSWORD SUCCESS");
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUsername(int playerID, String newUsername) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE player SET name = ? WHERE playerID = ?");
            stmt.setString(1, newUsername);
            stmt.setInt(2, playerID);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("UPDATE USERNAME SUCCESS");
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePlayer(int playerID) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM player WHERE playerID = ?");
            stmt.setInt(1, playerID);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("DELETE PLAYER SUCCESS");
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
