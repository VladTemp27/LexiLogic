package org.amalgam.backend.microservices.game;

import org.amalgam.Utils.Exceptions.InsufficientWordPointsException;
import org.amalgam.Utils.Exceptions.InvalidTotalPointsException;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class ValidateTotalPointsMicroservice {
    private final ORBConnection orbConnection;

    public ValidateTotalPointsMicroservice(ORBConnection orbConnection) {
        this.orbConnection = new ORBConnection(1099, "localhost");
    }

    public int process (){
        try {
            return orbConnection.retrieveGameService().validateTotalPoints();
        } catch (InsufficientWordPointsException | InvalidTotalPointsException | InvalidName | CannotProceed |
                 NotFound e) {
            throw new RuntimeException(e);
        }
    }
}

