package org.amalgam.backend.microservices.game;

import org.amalgam.backend.microservices.serverconnection.ORBConnection;

public class PlayerReady {
    public void process(ORBConnection orbConnection, String username, int gameRoomID) {
        try {
            orbConnection.retrieveGameService().playerReady(username, gameRoomID);
        } catch (Exception e){
            new RuntimeException(e);
        }
    }
}
