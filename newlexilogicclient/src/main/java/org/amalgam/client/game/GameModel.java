package org.amalgam.client.game;

import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.backend.microservices.game.FetchWinnerMicroservice;
import org.amalgam.backend.microservices.game.FetchWordBoxMicroservice;
import org.amalgam.backend.microservices.game.VerifyWordMicroservice;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;

public class GameModel {
    private final ORBConnection orbConnection;
    private final FetchWinnerMicroservice fetchWinnerMicroservice;
    private FetchWordBoxMicroservice fetchWordBoxMicroservice;
    private VerifyWordMicroservice verifyWordMicroservice;
    public GameModel (ORBConnection orbConnection) {
        this.orbConnection = orbConnection;
        fetchWinnerMicroservice = new FetchWinnerMicroservice();
        fetchWordBoxMicroservice = new FetchWordBoxMicroservice();
        verifyWordMicroservice = new VerifyWordMicroservice();
    }

    public String fetchWinner (int lobbyID){
        return fetchWinnerMicroservice.process(orbConnection, lobbyID);
    }

    public char [][] fetchWordBox (int roomID){
       return fetchWordBoxMicroservice.process(orbConnection, roomID);
    }

    public void verifyWord (String word){
        verifyWordMicroservice.process(orbConnection,word);
    }

}
