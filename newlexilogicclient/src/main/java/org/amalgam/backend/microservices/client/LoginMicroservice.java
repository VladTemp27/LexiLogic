package org.amalgam.backend.microservices.client;

import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.Utils.Exceptions.AlreadyLoggedInException;
import org.amalgam.Utils.Exceptions.InvalidCredentialsException;
import org.amalgam.Utils.Exceptions.UserExistenceException;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class LoginMicroservice {

    private final ORBConnection orbConnection;

    public LoginMicroservice() {
        this.orbConnection = new ORBConnection(1099, "localhost");
    }

    public void process (PlayerCallback playerCallback, String password) {
        try {
            orbConnection.retrievePlayerRequestStub().login(playerCallback, password);
        } catch (AlreadyLoggedInException | InvalidCredentialsException | UserExistenceException | InvalidName |
                 CannotProceed | NotFound e){
            throw new RuntimeException(e);
        }
    }

}
