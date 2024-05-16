package org.amalgam.client.login;

import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.Utils.Exceptions.AlreadyLoggedInException;
import org.amalgam.Utils.Exceptions.InvalidCredentialsException;
import org.amalgam.Utils.Exceptions.UserExistenceException;
import org.amalgam.backend.microservices.client.LoginMicroservice;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class LoginModel {
    private final ORBConnection orbConnection;
    private final LoginMicroservice loginMicroservice;
    private PlayerCallback playerCallback;


    public LoginModel (ORBConnection orbConnection, PlayerCallback playerCallback){
        this.orbConnection = orbConnection;
        this.playerCallback = playerCallback;
        this.loginMicroservice = new LoginMicroservice();
    }

    public  boolean login (String password) {
        try {
            loginMicroservice.process(orbConnection, playerCallback, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public void setPlayerCallback(PlayerCallback playerCallback) {
        this.playerCallback = playerCallback;
    }
}
