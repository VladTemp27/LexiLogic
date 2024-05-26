package org.amalgam.client.leaderboards;

import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;

public class LeaderboardsModel {

    ORBConnection orbConnection;
    org.amalgam.backend.microservices.game.GetLeaderBoards leaderBoardsMicroservice;

    public LeaderboardsModel (ORBConnection orbConnection, PlayerCallback playerCallback){
        this.orbConnection = orbConnection;
        this.leaderBoardsMicroservice = new org.amalgam.backend.microservices.game.GetLeaderBoards();
    }

    public String getLeaderBoards () {
       return leaderBoardsMicroservice.process(orbConnection);
    }

}
