package org.amalgam.backend.microservices.game;

import org.amalgam.Utils.Exceptions.EmptyLeaderBoardException;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class GetLeaderBoardsMicroservice {
    private final ORBConnection orbConnection;

    public GetLeaderBoardsMicroservice(ORBConnection orbConnection) {
        this.orbConnection = new ORBConnection(1099, "localhost");
    }

    public String process (){
        try {
            return orbConnection.retrieveGameService().getLeaderboards();
        } catch (EmptyLeaderBoardException | InvalidName | CannotProceed | NotFound e) {
            throw new RuntimeException(e);
        }
    }
}
