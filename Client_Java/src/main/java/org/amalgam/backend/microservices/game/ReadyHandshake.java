package org.amalgam.backend.microservices.game;

import org.amalgam.backend.microservices.serverconnection.ORBConnection;

public class ReadyHandshake {
    public void process(ORBConnection orbConnection, String username, int gameRoomID) {
        try {
            orbConnection.retrieveGameService().readyHandshake(username, gameRoomID);
        } catch (Exception e){
            new RuntimeException(e);
        }
    }
}
