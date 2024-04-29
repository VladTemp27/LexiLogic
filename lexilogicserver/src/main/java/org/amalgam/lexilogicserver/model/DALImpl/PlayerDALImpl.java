package org.amalgam.lexilogicserver.model.DALImpl;

import org.amalgam.DAL.DALPlayer.PlayerDALPOA;
import org.amalgam.DAL.DALPlayer.PlayerDALPackage.Player;
import org.amalgam.DAL.SQLExceptions.SQLCreateError;
import org.amalgam.DAL.SQLExceptions.SQLDeleteError;
import org.amalgam.DAL.SQLExceptions.SQLRetrieveError;
import org.amalgam.DAL.SQLExceptions.SQLUpdateError;

public class PlayerDALImpl extends PlayerDALPOA {
    @Override
    public void insertNewPlayer(String username, String password, String lastLogin) throws SQLCreateError {

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
