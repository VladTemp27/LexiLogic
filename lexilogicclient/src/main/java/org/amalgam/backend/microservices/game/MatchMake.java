package org.amalgam.backend.microservices.game;

import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.Utils.Exceptions.MatchCreationFailedException;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class MatchMake {

    public String process (ORBConnection orbConnection, PlayerCallback playerCallback){
        try {
            //TODO: Change the overall structure of the game to make place for matchmake_callback
          return orbConnection.retrieveGameService().matchMake(playerCallback, null);
        } catch (MatchCreationFailedException | InvalidName | CannotProceed | NotFound e) {
            throw new RuntimeException(e);
        }
    }
}
