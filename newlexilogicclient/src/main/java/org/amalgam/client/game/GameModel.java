package org.amalgam.client.game;

import org.amalgam.backend.microservices.game.FetchWinnerMicroservice;
import org.amalgam.backend.microservices.game.FetchWordBoxMicroservice;
import org.amalgam.backend.microservices.game.PlayerReadyMicroservice;
import org.amalgam.backend.microservices.game.VerifyWordMicroservice;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;

public class GameModel {
    private final ORBConnection orbConnection;
    private PlayerReadyMicroservice playerReadyMicroservice;
    private VerifyWordMicroservice verifyWordMicroservice;
    public GameModel (ORBConnection orbConnection) {
        this.orbConnection = orbConnection;
        playerReadyMicroservice = new PlayerReadyMicroservice();
        verifyWordMicroservice = new VerifyWordMicroservice();
    }

    public void submitReadyPlayer(String username, int gameRoomID) {
        playerReadyMicroservice.process(orbConnection, username, gameRoomID);
    }
    public void verifyWord (String word){
        verifyWordMicroservice.process(orbConnection,word);
    }

}
