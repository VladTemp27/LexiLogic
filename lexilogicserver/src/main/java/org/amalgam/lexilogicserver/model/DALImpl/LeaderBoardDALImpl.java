package org.amalgam.lexilogicserver.model.DALImpl;

import org.amalgam.DAL.DALLeaderBoard.LeaderboardDALPOA;
import org.amalgam.DAL.DALLeaderBoard.LeaderboardDALPackage.Leaderboard;
import org.amalgam.DAL.SQLExceptions.SQLCreateError;
import org.amalgam.DAL.SQLExceptions.SQLRetrieveError;

public class LeaderBoardDALImpl extends LeaderboardDALPOA {
    @Override
    public void insertNewLeaderboard(int userID, int totalPoints) throws SQLCreateError {

    }

    @Override
    public Leaderboard getLeaderboardByID(int leaderboardID) throws SQLRetrieveError {
        return null;
    }
}
