package org.amalgam.backend.microservices.client;

import org.amalgam.Utils.Exceptions.NotLoggedInException;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class LogoutRequest {

    public void process (ORBConnection orbConnection, String username){
        try {
            orbConnection.retrievePlayerRequestStub().logout(username);

        } catch (InvalidName | CannotProceed | NotLoggedInException | NotFound e) {
            throw new RuntimeException(e);
        }
    }
}
