package org.amalgam.backend.microservices;

import org.amalgam.Service.PlayerServiceModule._PlayerServiceStub;
import org.amalgam.Utils.Exceptions.GameHistoryUnavailableException;
import org.amalgam.Utils.Exceptions.InGameException;
import org.amalgam.Utils.Objects.Lobby;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class FetchMatchHistoryService {
    ORBConnection orbConnection = new ORBConnection(1099, "hostname");

    public Lobby[] process(String playerName) throws InvalidName, CannotProceed, NotFound {
        try {
            return orbConnection.retrievePlayerRequestStub().getGameHistory(playerName);
        } catch (GameHistoryUnavailableException e) {
            throw new RuntimeException(e);
        } catch (InGameException e) {
            throw new RuntimeException(e);
        }
    }
}
