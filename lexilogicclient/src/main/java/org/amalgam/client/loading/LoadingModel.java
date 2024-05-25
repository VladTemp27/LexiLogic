package org.amalgam.client.loading;

import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.backend.microservices.game.MatchMakeMicroservice;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;

public class LoadingModel {
    private final ORBConnection orbConnection;
    private final MatchMakeMicroservice matchMakeMicroservice;
    public PlayerCallback playerCallback;
    public LoadingModel (ORBConnection orbConnection, PlayerCallback playerCallback){
        this.orbConnection = orbConnection;
        this.playerCallback = playerCallback;
        this.matchMakeMicroservice = new MatchMakeMicroservice();
    }
    public String matchMake(){
        return matchMakeMicroservice.process(orbConnection, playerCallback);
    }
    public void setPlayerCallback(PlayerCallback playerCallback) {
        this.playerCallback = playerCallback;
    }

    public PlayerCallback getPlayerCallback() {
        return playerCallback;
    }
}
