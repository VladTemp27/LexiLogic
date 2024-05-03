package org.amalgam.lexilogicserver.model.DALImpl;

import org.amalgam.DAL.DALGameDetail.GameDetailsDALPOA;
import org.amalgam.DAL.DALGameDetail.GameDetailsDALPackage.GameDetail;
import org.amalgam.DAL.SQLExceptions.SQLCreateError;
import org.amalgam.DAL.SQLExceptions.SQLRetrieveError;

public class GameDetailDALImpl extends GameDetailsDALPOA {
    @Override
    public void insertNewGameDetail(int playerID, int lobbyID, int totalPoints) throws SQLCreateError {

    }

    @Override
    public GameDetail getGameDetailByID(int lobbyID) throws SQLRetrieveError {
        return null;
    }
}
