package org.amalgam.backend.microservices;
import org.amalgam.Utils.Exceptions.AccountCreationFailedException;
import org.amalgam.lexilogicserver.model.utilities.corbautils.PlayerImpl;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;

public class SignUpMicroservice {

    ORBConnection orbConnection = new ORBConnection(1099,"hostname");

    public  void process (String username, String password){

        PlayerImpl newPlayer = new PlayerImpl(username, password);

        try {
            orbConnection.retrievePlayerRequestStub().createAccount(newPlayer);
        } catch (AccountCreationFailedException e) {
            throw new RuntimeException(e);
        }
    }

}
