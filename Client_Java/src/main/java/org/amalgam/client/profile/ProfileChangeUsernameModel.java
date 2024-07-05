package org.amalgam.client.profile;

import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.backend.microservices.client.ChangeUsernameRequest;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;

public class ProfileChangeUsernameModel {
    private ORBConnection orbConnection;
    private PlayerCallback playerCallback;
    private ChangeUsernameRequest changeUsernameRequestMicroservice;

    public ProfileChangeUsernameModel (ORBConnection orbConnection, PlayerCallback playerCallback){
        this.orbConnection = orbConnection;
        this.playerCallback = playerCallback;
        changeUsernameRequestMicroservice = new ChangeUsernameRequest();
    }

    public void changeUsername (String newUsername){
        changeUsernameRequestMicroservice.process(orbConnection, playerCallback.username(), newUsername);
    }

}
