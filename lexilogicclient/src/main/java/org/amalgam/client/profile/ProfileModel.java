package org.amalgam.client.profile;

import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.backend.microservices.client.AccountDeletionRequest;
import org.amalgam.backend.microservices.client.LogoutRequest;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;
import org.amalgam.client.login.LoginController;

public class ProfileModel {

    ORBConnection orbConnection;
    PlayerCallback playerCallback;
    private AccountDeletionRequest accountDeletionRequestMicroservice;
    private LogoutRequest logOutMicroservice;
    public static String username = LoginController.username;
    public ProfileModel(ORBConnection orbConnection, PlayerCallback playerCallback){
        this.orbConnection = orbConnection;
        this.playerCallback = playerCallback;
        accountDeletionRequestMicroservice = new AccountDeletionRequest();
        logOutMicroservice = new LogoutRequest();
    }

    public static void setUsername(String newUsername){
        username = newUsername;
    }

    public void accountDeletionRequest (){
        accountDeletionRequestMicroservice.process(orbConnection, playerCallback.username());
    }

    public void logOut (){
        logOutMicroservice.process(orbConnection, playerCallback.username());
    }

}
