package org.amalgam.client.matchhistory;

import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.backend.microservices.client.GetGameHistoryMicroservice;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;

public class MatchHistoryModel {

    private ORBConnection orbConnection;
    private PlayerCallback playerCallback;
    private GetGameHistoryMicroservice gameHistoryMicroservice;

    public MatchHistoryModel (ORBConnection orbConnection, PlayerCallback playerCallback){
        this.orbConnection = orbConnection;
        this.playerCallback = playerCallback;
        this.gameHistoryMicroservice = new GetGameHistoryMicroservice();
    }

    public String getMatchHistory () {
       return gameHistoryMicroservice.process(orbConnection, playerCallback.username());
    }
}
