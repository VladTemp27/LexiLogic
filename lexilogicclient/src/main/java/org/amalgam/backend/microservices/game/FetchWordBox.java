package org.amalgam.backend.microservices.game;

import org.amalgam.Utils.Exceptions.InvalidRoomIDException;
import org.amalgam.Utils.Exceptions.WordFetchFailedException;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class FetchWordBox {

    public char [][] process (ORBConnection orbConnection, int roomID){
        try {
            return orbConnection.retrieveGameService().fetchWordBox(roomID);
        } catch (WordFetchFailedException | InvalidRoomIDException | InvalidName | CannotProceed | NotFound e) {
            throw new RuntimeException(e);
        }
    }
}
