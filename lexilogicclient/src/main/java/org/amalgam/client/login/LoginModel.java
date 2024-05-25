package org.amalgam.client.login;

import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.backend.microservices.client.LoginRequest;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;

public class LoginModel {
    private final ORBConnection orbConnection;
    private final LoginRequest loginRequest;
    public static PlayerCallback playerCallback;
    public LoginModel (ORBConnection orbConnection, PlayerCallback playerCallback){
        this.orbConnection = orbConnection;
        LoginModel.playerCallback = playerCallback;
        this.loginRequest = new LoginRequest();
    }

    public  boolean login (String password) {
        try {
            loginRequest.process(orbConnection, playerCallback, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public void setPlayerCallback(PlayerCallback playerCallback) {
        LoginModel.playerCallback = playerCallback;
    }
}
