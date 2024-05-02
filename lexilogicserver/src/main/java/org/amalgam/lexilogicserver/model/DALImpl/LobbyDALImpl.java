package org.amalgam.lexilogicserver.model.DALImpl;

import org.amalgam.DAL.DALLobby.LobbyDALPOA;
import org.amalgam.DAL.DALLobby.LobbyDALPackage.Lobby;
import org.amalgam.DAL.SQLExceptions.SQLCreateError;
import org.amalgam.DAL.SQLExceptions.SQLRetrieveError;

public class LobbyDALImpl extends LobbyDALPOA {
    @Override
    public void insertNewLobby(int lobbyId, String createdBy, String winner) throws SQLCreateError {

    }

    @Override
    public Lobby getLobbyByID(int lobbyId) throws SQLRetrieveError {
        return null;
    }
}
