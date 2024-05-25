package org.amalgam.backend.microservices.client;

import org.amalgam.Utils.Exceptions.DeleteAccountFailedException;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class AccountDeletionRequestMicroservice {

    public void process (ORBConnection orbConnection, String username){
        try {
            orbConnection.retrievePlayerRequestStub().accountDeletionRequest(username);
        } catch (DeleteAccountFailedException | InvalidName | CannotProceed | NotFound e) {
            throw new RuntimeException(e);
        }
    }
}