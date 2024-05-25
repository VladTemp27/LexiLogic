package org.amalgam.client.profile;

import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.backend.microservices.client.AccountDeletionRequestMicroservice;
import org.amalgam.backend.microservices.client.LogOutMicroservice;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;

public class ProfileModel {

    ORBConnection orbConnection;
    PlayerCallback playerCallback;
    private AccountDeletionRequestMicroservice accountDeletionRequestMicroservice;
    private LogOutMicroservice logOutMicroservice;
    public ProfileModel(ORBConnection orbConnection, PlayerCallback playerCallback){
        this.orbConnection = orbConnection;
        this.playerCallback = playerCallback;
        accountDeletionRequestMicroservice = new AccountDeletionRequestMicroservice();
        logOutMicroservice = new LogOutMicroservice();
    }

    public void accountDeletionRequest (){
        accountDeletionRequestMicroservice.process(orbConnection, playerCallback.username());
    }

    public void logOut (){
        logOutMicroservice.process(orbConnection, playerCallback.username());
    }

}
