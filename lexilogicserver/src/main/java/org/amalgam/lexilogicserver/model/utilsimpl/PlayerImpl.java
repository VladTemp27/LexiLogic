package org.amalgam.lexilogicserver.model.utilsimpl;

import org.amalgam.Utils.ObjectExceptions.*;
import org.amalgam.Utils.Objects.Player;
import org.amalgam.Utils.Objects.PlayerPOA;


// TODO: add throwing of exceptions caused by bad params, discuss deprecated classes with the team
public class PlayerImpl extends PlayerPOA {
    private int id;
    private String name;
    private String password;
    private String lastLoggedIn;
    @Override
    public int id() {
        return this.id;
    }

    @Override
    public void id(int newId) {
        this.id = newId;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public void name(String newName) {
        this.name = newName;
    }

    @Override
    public String password() {
        return this.password;
    }

    @Override
    public void password(String newPassword) {
        this.password = newPassword;
    }

    @Override
    public String lastLogin() {
        return "";
    }

    @Override
    public void lastLogin(String newLastLogin) {
        this.lastLoggedIn = newLastLogin;
    }

    /**
     * @deprecated
     * Should be under PlayerService not reference class
     *
     * @param player
     * @throws DuplicatePlayerIDException
     * @throws InvalidPlayerDataException
     * @throws SQLError
     */
    @Deprecated
    @Override
    public void addPlayer(Player player) throws DuplicatePlayerIDException, InvalidPlayerDataException, SQLError {

    }

    @Override
    public void updateName(String newName) throws InvalidNameException, SQLError {
        this.name = newName;
    }

    @Override
    public void updatePassword(String newPassword) throws InvalidPasswordException, SQLError {
        this.password = newPassword;
    }

    @Override
    public void updateLastLogin(String newTime) throws UpdateLastLoginException, SQLError {
        this.lastLoggedIn = newTime;
    }

    /**
     * @deprecated
     * This should be under PlayerService not inside the reference class of player (this)
     * @param id
     * @throws PlayerDoesNotExistException
     */
    @Deprecated
    @Override
    public void deletePlayer(int id) throws PlayerDoesNotExistException {

    }
}
