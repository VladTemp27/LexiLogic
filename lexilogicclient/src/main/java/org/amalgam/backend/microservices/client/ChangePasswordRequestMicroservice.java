package org.amalgam.backend.microservices.client;

import org.amalgam.Utils.Exceptions.ChangePasswordFailedException;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class ChangePasswordRequestMicroservice {


    public void process (ORBConnection orbConnection,String username, String newPassword){
        try {
            orbConnection.retrievePlayerRequestStub().changePassword(username,newPassword);
        } catch (ChangePasswordFailedException | InvalidName | CannotProceed | NotFound e) {
            throw new RuntimeException(e);
        }
    }
}
