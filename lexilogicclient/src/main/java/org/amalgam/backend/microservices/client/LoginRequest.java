package org.amalgam.backend.microservices.client;

import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.Utils.Exceptions.AlreadyLoggedInException;
import org.amalgam.Utils.Exceptions.InvalidCredentialsException;
import org.amalgam.Utils.Exceptions.UserExistenceException;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class LoginRequest {
    public boolean process (ORBConnection orbConnection, PlayerCallback playerCallback, String password) throws AlreadyLoggedInException, InvalidCredentialsException, InvalidName, CannotProceed, NotFound, UserExistenceException {
        orbConnection.retrievePlayerRequestStub().login(playerCallback, password);
        return true;
    }
}
