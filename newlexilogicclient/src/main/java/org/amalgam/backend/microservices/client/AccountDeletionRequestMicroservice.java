package org.amalgam.backend.microservices.client;

import org.amalgam.Utils.Exceptions.DeleteAccountFailedException;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class AccountDeletionRequestMicroservice {
    private final ORBConnection orbConnection;


    public AccountDeletionRequestMicroservice(ORBConnection orbConnection) {
        this.orbConnection = new ORBConnection(1099, "localhost");
    }

    public void process (String username){
        try {
            orbConnection.retrievePlayerRequestStub().accountDeletionRequest(username);
        } catch (DeleteAccountFailedException | InvalidName | CannotProceed | NotFound e) {
            throw new RuntimeException(e);
        }
    }
}