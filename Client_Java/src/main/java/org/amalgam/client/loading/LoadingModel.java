package org.amalgam.client.loading;

import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;

public class LoadingModel {
    private final ORBConnection orbConnection;
    private final org.amalgam.backend.microservices.game.MatchMake matchMake;
    public PlayerCallback playerCallback;
    public LoadingModel (ORBConnection orbConnection, PlayerCallback playerCallback){
        this.orbConnection = orbConnection;
        this.playerCallback = playerCallback;
        this.matchMake = new org.amalgam.backend.microservices.game.MatchMake();
    }
    public String matchMake(){
        return matchMake.process(orbConnection, playerCallback);
    }
    public void setPlayerCallback(PlayerCallback playerCallback) {
        this.playerCallback = playerCallback;
    }

    public PlayerCallback getPlayerCallback() {
        return playerCallback;
    }
}
