package org.amalgam.backend.microservices.client;

import org.amalgam.Utils.Exceptions.GameHistoryUnavailableException;
import org.amalgam.Utils.Exceptions.InGameException;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class GetGameHistoryMicroservice {

    public String process (ORBConnection orbConnection, String username){
        try {
           return orbConnection.retrievePlayerRequestStub().getGameHistory(username);
        } catch (GameHistoryUnavailableException | InGameException | InvalidName | CannotProceed | NotFound e) {
            throw new RuntimeException(e);
        }
    }
}
