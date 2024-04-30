package org.amalgam.lexilogicserver.model.DALImpl;

import org.amalgam.DAL.DALPlayer.PlayerDALPOA;
import org.amalgam.DAL.DALPlayer.PlayerDALPackage.Player;
import org.amalgam.DAL.SQLExceptions.SQLCreateError;
import org.amalgam.DAL.SQLExceptions.SQLDeleteError;
import org.amalgam.DAL.SQLExceptions.SQLRetrieveError;
import org.amalgam.DAL.SQLExceptions.SQLUpdateError;
import org.amalgam.lexilogicserver.model.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
            System.out.println("true");
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Player getPlayerByID(int playerID) throws SQLRetrieveError {
        return null;
    }

    @Override
    public void updatePassword(int playerID, String newPassword) throws SQLUpdateError {

    }

    @Override
    public void updateUsername(int playerID, String newUsername) throws SQLUpdateError {

    }

    @Override
    public void deletePlayer(int playerID) throws SQLDeleteError {

    }
}
