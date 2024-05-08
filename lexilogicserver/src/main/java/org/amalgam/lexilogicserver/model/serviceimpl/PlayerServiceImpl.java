package org.amalgam.lexilogicserver.model.serviceimpl;

import org.amalgam.Service.PlayerExceptions.*;
import org.amalgam.Service.PlayerServiceModule.PlayerCallback;
import org.amalgam.Service.PlayerServiceModule.PlayerServicePOA;
import org.amalgam.Utils.Exceptions.*;
import org.amalgam.Utils.Exceptions.InGameException;
import org.amalgam.Utils.Exceptions.UserExistenceException;
import org.amalgam.Utils.Objects.GameRoom;
import org.amalgam.Utils.Objects.Lobby;
import org.amalgam.Utils.Objects.Player;
import org.amalgam.lexilogicserver.model.DAL.PlayerDAL;

import java.util.LinkedList;

public class PlayerServiceImpl extends PlayerServicePOA {
    LinkedList<PlayerCallback> playerSessions = new LinkedList<>();


    @Override
    public void login(org.amalgam.Utils.UIControllers.PlayerCallback player_callback) throws AlreadyLoggedInException, InvalidCredentialsException, UserExistenceException {

    }

    @Override
    public void logout() throws NotLoggedInException {

    }

    @Override
    public void createAccount(Player newPlayer) throws AccountCreationFailedException {
        String username = newPlayer.name();
        String password = newPlayer.password();
        String lastLogin = newPlayer.lastLogin();
        PlayerDAL.insertNewPlayer(username, password,lastLogin);
    }

    @Override
    public void changeUsername(String username, String newUsername) throws ChangeUsernameFailedException {
        int userId = PlayerDAL.getIDByUsername(username);
        PlayerDAL.updateUsername(userId, newUsername);
    }

    @Override
    public void changePassword(String username, String newPassword) throws ChangePasswordFailedException {
        int userId = PlayerDAL.getIDByUsername(username);
        PlayerDAL.updatePassword(userId, newPassword);
    }

    @Override
    public void accountDeletionRequest(String username) throws DeleteAccountFailedException {

    }

    @Deprecated
    @Override
    public String getGameResult(String playerName) throws GameResultUnavailableException, InGameException {
        return "";
    }

    @Override
    public Lobby[] getGameHistory(String playerName) throws GameHistoryUnavailableException, InGameException {
        return new Lobby[0];
    }
}
