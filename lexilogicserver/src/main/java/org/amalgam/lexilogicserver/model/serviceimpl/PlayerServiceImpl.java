package org.amalgam.lexilogicserver.model.serviceimpl;

import org.amalgam.Service.PlayerExceptions.*;
import org.amalgam.Service.PlayerServiceModule.PlayerCallback;
import org.amalgam.Service.PlayerServiceModule.PlayerServicePOA;
import org.amalgam.Utils.Objects.GameRoom;
import org.amalgam.lexilogicserver.model.utilsimpl.GameRoomImpl;

import java.util.LinkedList;

public class PlayerServiceImpl extends PlayerServicePOA {
    LinkedList<PlayerCallbackImpl> playerSessions = new LinkedList<>();
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

    /**
     * @deprecated
     * should be done under game service
     * @param word
     * @param username
     * @throws SubmitWordFailed
     */
    @Deprecated
    @Override
    public void submitWord(String word, String username) throws SubmitWordFailed {

    }

    /**
     * @deprecated
     *Is another term for matchmake, should be under gameservice
     * @param username
     * @throws StartGameFailed
     * @throws InGameException
     */
    @Deprecated
    @Override
    public void startGame(String username) throws StartGameFailed, InGameException {

    }

    @Deprecated
    @Override
    public GameRoom matchMake(PlayerCallback player_callback) throws MatchCreationFailed {
        return null;
    }

    @Deprecated
    @Override
    public String getGameResult(String playerName) throws GameResultUnavailable, InGameException {
        return "";
    }

    @Override
    public void getGameHistory(String playerName) throws GameHistoryUnavailable, InGameException {

    }
}
