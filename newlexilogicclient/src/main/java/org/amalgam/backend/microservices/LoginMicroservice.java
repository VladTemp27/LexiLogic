package org.amalgam.backend.microservices;

import org.amalgam.Utils.Exceptions.AlreadyLoggedInException;
import org.amalgam.Utils.Exceptions.InvalidCredentialsException;
import org.amalgam.Utils.Exceptions.UserExistenceException;
import org.amalgam.Utils.UIControllers.PlayerCallback;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;

public class LoginMicroservice {

    ORBConnection orbConnection = new ORBConnection(1099, "hostname");

    public void process (PlayerCallback playerCallback) {
        try {
            orbConnection.retrievePlayerRequestStub().login(playerCallback);
        } catch (AlreadyLoggedInException | InvalidCredentialsException | UserExistenceException e){
            throw new RuntimeException(e);
        }
    }

}
