package org.amalgam.client.mainmenu;

import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.backend.microservices.game.MatchMake;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;


public class MainMenuModel {
    private final ORBConnection orbConnection;
    private PlayerCallback playerCallback;
    private MatchMake matchMake;

    public MainMenuModel(ORBConnection orbConnection, PlayerCallback playerCallback){
        this.orbConnection = orbConnection;
        this.playerCallback = playerCallback;
        matchMake = new MatchMake();
    }

//    public String matchMake(){
//        return matchMakeMicroservice.process(orbConnection, playerCallback);
//    }
}
