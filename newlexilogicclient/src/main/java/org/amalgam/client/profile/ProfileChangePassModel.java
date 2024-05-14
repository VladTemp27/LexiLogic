package org.amalgam.client.profile;

import org.amalgam.ControllerException.InvalidRequestException;
import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.backend.microservices.client.ChangePasswordRequestMicroservice;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;

public class ProfileChangePassModel {
    private final ORBConnection orbConnection;
    private final PlayerCallback playerCallback;
    private final ChangePasswordRequestMicroservice changePasswordRequest;

    public ProfileChangePassModel (ORBConnection orbConnection, PlayerCallback playerCallback){
        this.orbConnection = orbConnection;
        this.playerCallback = playerCallback;
        this.changePasswordRequest = new ChangePasswordRequestMicroservice();
    }

    public void changePassword (String password) {
        changePasswordRequest.process(orbConnection, playerCallback.username(), password);
    }
}
