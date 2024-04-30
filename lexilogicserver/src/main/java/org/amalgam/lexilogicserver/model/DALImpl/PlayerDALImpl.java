package org.amalgam.lexilogicserver.model.DALImpl;

import org.amalgam.DAL.DALPlayer.PlayerDALPOA;
import org.amalgam.DAL.DALPlayer.PlayerDALPackage.Player;
import org.amalgam.DAL.SQLExceptions.SQLCreateError;
import org.amalgam.DAL.SQLExceptions.SQLDeleteError;
import org.amalgam.DAL.SQLExceptions.SQLRetrieveError;
import org.amalgam.DAL.SQLExceptions.SQLUpdateError;
import org.amalgam.lexilogicserver.model.DatabaseUtil;

import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDALImpl extends PlayerDALPOA {
    @Override
    public void insertNewPlayer(String username, String password, String lastLogin) throws SQLCreateError {
        try(Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO player (name, password, lastLogin) VALUE (?,?,?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, lastLogin);
            preparedStatement.execute();
            System.out.println("CREATE NEW PLAYER SUCCESS");
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Player getPlayerByID(int playerID) throws SQLRetrieveError {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM player WHERE playerID = ?");
            stmt.setInt(1, playerID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()){
                    int fetchedPlayerID = rs.getInt("playerID");
                    String fetchedUsername = rs.getString("name");
                    String fetchedPassword = rs.getString("password");
                    String fetchedLastLogin = rs.getString("lastLogin");

                    return new Player(fetchedPlayerID,fetchedUsername,fetchedPassword,fetchedLastLogin);
                } else {
                    return null;
                }
            }

		} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
    }

    @Override
    public void updatePassword(int playerID, String newPassword) throws SQLUpdateError {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE player SET password = ? WHERE playerID = ?");
            stmt.setString(1, newPassword);
            stmt.setInt(1, playerID);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0 ) System.out.println("UPDATE PASSWORD SUCCESS");
		} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

    @Override
    public void updateUsername(int playerID, String newUsername) throws SQLUpdateError {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE player SET name = ? WHERE playerID = ?");
            stmt.setString(1, newUsername);
            stmt.setInt(1, playerID);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) System.out.println("UPDATE USERNAME SUCCESS");
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePlayer(int playerID) throws SQLDeleteError {
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM player WHERE playerID = ? ");
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) System.out.println("DELETE PLAYER SUCCESS");
		} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
