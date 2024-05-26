package org.amalgam.client.game;

import org.amalgam.backend.microservices.game.PlayerReady;
import org.amalgam.backend.microservices.game.VerifyWord;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;

public class GameModel{
    private final ORBConnection orbConnection;
    private final PlayerReady playerReady;
    private final VerifyWord verifyWord;
    public GameModel (ORBConnection orbConnection) {
        this.orbConnection = orbConnection;
        playerReady = new PlayerReady();
        verifyWord = new VerifyWord();
    }

    public void submitReadyPlayer(String username, int gameRoomID) {
        playerReady.process(orbConnection, username, gameRoomID);
    }
    public void verifyWord(String word, String username, int gameRoomID){
        verifyWord.process(orbConnection,word, username, gameRoomID);
    }

}
