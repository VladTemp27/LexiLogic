package org.amalgam.backend.microservices.client;

import org.amalgam.Utils.Exceptions.ChangeUsernameFailedException;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class ChangeUsernameRequest {
    public void process (ORBConnection orbConnection, String username, String newUsername){
        try {
            orbConnection.retrievePlayerRequestStub().changeUsername(username,newUsername);
        } catch (ChangeUsernameFailedException | InvalidName | CannotProceed | NotFound e) {
            throw new RuntimeException(e);
        }
    }
}
