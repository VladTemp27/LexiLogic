package org.amalgam.backend.microservices.game;

import org.amalgam.Utils.Exceptions.DuplicateWordException;
import org.amalgam.Utils.Exceptions.InvalidWordFormatException;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class VerifyWord {
    public void process(ORBConnection orbConnection, String word, String username, int gameRoomID){
        try {
            orbConnection.retrieveGameService().verifyWord(word, username, gameRoomID);
        } catch (InvalidWordFormatException | DuplicateWordException | InvalidName | CannotProceed | NotFound e) {
            throw new RuntimeException(e);
        }
    }
}
