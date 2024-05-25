package org.amalgam.client.leaderboards;

import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.backend.microservices.game.GetLeaderBoardsMicroservice;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;

public class LeaderboardsModel {

    ORBConnection orbConnection;
    GetLeaderBoardsMicroservice leaderBoardsMicroservice;

    public LeaderboardsModel (ORBConnection orbConnection, PlayerCallback playerCallback){
        this.orbConnection = orbConnection;
        this.leaderBoardsMicroservice = new GetLeaderBoardsMicroservice();
    }

    public String getLeaderBoards () {
       return leaderBoardsMicroservice.process(orbConnection);
    }

}
