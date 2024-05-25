package org.amalgam.client.mainmenu;

import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.backend.microservices.game.MatchMakeMicroservice;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;


public class MainMenuModel {
    private final ORBConnection orbConnection;
    private PlayerCallback playerCallback;
    private MatchMakeMicroservice matchMakeMicroservice;

    public MainMenuModel(ORBConnection orbConnection, PlayerCallback playerCallback){
        this.orbConnection = orbConnection;
        this.playerCallback = playerCallback;
        matchMakeMicroservice = new MatchMakeMicroservice();
    }

//    public String matchMake(){
//        return matchMakeMicroservice.process(orbConnection, playerCallback);
//    }
}
