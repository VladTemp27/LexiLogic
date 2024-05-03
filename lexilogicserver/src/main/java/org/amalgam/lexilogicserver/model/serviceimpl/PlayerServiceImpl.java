package org.amalgam.lexilogicserver.model.serviceimpl;

import org.amalgam.Service.PlayerExceptions.*;
import org.amalgam.Service.PlayerServiceModule.PlayerCallback;
import org.amalgam.Service.PlayerServiceModule.PlayerServicePOA;
import org.amalgam.Utils.Objects.GameRoom;
import org.amalgam.lexilogicserver.model.utilsimpl.GameRoomImpl;

import java.util.LinkedList;

public class PlayerServiceImpl extends PlayerServicePOA {
    LinkedList<PlayerCallbackImpl> playerSessions = new LinkedList<>();
    LinkedList<GameRoomImpl> gameSessions = new LinkedList<>();
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
    public GameRoom matchMake(PlayerCallback player_callback) throws MatchCreationFailed {
        return null;
    }

    @Override
    public String getGameResult(String playerName) throws GameResultUnavailable, InGameException {
        return "";
    }

    @Override
    public void getGameHistory(String playerName) throws GameHistoryUnavailable, InGameException {

    }
}
