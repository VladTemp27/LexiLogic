package org.amalgam.lexilogicserver.model.microservices;
import org.amalgam.Utils.Exceptions.AccountCreationFailedException;
import org.amalgam.lexilogicserver.model.DAL.PlayerDAL;

public class SignUpMicroservice {


    public  void process (String username, String password){

        try {
            PlayerDAL.insertNewPlayer(username, password);
        } catch (AccountCreationFailedException e) {
            throw new RuntimeException(e);
        }
    }

}
