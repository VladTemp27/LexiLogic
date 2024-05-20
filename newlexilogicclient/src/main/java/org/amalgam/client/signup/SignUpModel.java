package org.amalgam.client.signup;

import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.backend.microservices.client.CreateAccountRequestMicroservice;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;
@Deprecated
public class SignUpModel {
    private final ORBConnection orbConnection;
    private final CreateAccountRequestMicroservice signUpMicroservices;

    public SignUpModel (ORBConnection orbConnection, PlayerCallback playerCallback){
        this.orbConnection = orbConnection;
        this.signUpMicroservices = new CreateAccountRequestMicroservice();
    }

    public void signUp (String username, String password){
        signUpMicroservices.process(orbConnection,username, password);
    }
}
