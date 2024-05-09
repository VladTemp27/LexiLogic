package org.amalgam.backend.microservices;
import org.amalgam.Utils.Exceptions.AccountCreationFailedException;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.Player;

public class SignUpMicroservice {

    ORBConnection orbConnection = new ORBConnection(1099,"hostname");

    public  void process (String username, String password){

        Player newPlayer = new Player(username, password);

        try {
            orbConnection.retrievePlayerRequestStub().createAccount(newPlayer);
        } catch (AccountCreationFailedException e) {
            throw new RuntimeException(e);
        }
    }

}
