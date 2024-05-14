package org.amalgam.backend.microservices.client;

import org.amalgam.Utils.Exceptions.ChangeUsernameFailedException;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class ChangeUsernameRequestMicroservice {

    private final ORBConnection orbConnection;


    public ChangeUsernameRequestMicroservice(ORBConnection orbConnection) {
        this.orbConnection = new ORBConnection(1099, "localhost");
    }

    public void process (String username, String newUsername){
        try {
            orbConnection.retrievePlayerRequestStub().changeUsername(username,newUsername);
        } catch (ChangeUsernameFailedException | InvalidName | CannotProceed | NotFound e) {
            throw new RuntimeException(e);
        }
    }
}
