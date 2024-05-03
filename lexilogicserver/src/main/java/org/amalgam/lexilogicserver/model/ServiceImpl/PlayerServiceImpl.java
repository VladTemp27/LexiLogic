package org.amalgam.lexilogicserver.model.ServiceImpl;

import org.amalgam.Service.Player.PlayerCallback;
import org.amalgam.Service.Player.PlayerServicePOA;
import org.amalgam.Service.PlayerExceptions.*;

public class PlayerServiceImpl extends PlayerServicePOA {
    @Override
    public void login(PlayerCallback player_callback) throws AlreadyLoggedIn, InvalidCredentials, UserExistenceException {

    }

    @Override
    public void logout() throws NotLoggedIn {

    }

    @Override
    public void changeUsername(String username, String newUsername) throws ChangeUsernameFailed {

    }

    @Override
    public void changePassword(String username, String newPassword) throws ChangePasswordFailed {

    }

    @Override
    public void deleteAccount(String username) throws DeleteAccountFailed {

    }

    @Override
    public void submitWord(String word, String username) throws SubmitWordFailed {

    }

    @Override
    public void startGame(String username) throws StartGameFailed, InGameException {

    }

    @Override
    public void getGameResult() throws GameResultUnavailable, InGameException {

    }

    @Override
    public void getGameHistory() throws GameHistoryUnavailable, InGameException {

    }
}
