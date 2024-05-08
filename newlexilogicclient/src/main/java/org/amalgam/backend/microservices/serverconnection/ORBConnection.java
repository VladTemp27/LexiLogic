package org.amalgam.backend.microservices.serverconnection;

import org.amalgam.Service.GameServiceModule.GameService;
import org.amalgam.Service.GameServiceModule.GameServiceHelper;
import org.amalgam.Service.PlayerServiceModule.PlayerService;
import org.amalgam.Service.PlayerServiceModule.PlayerServiceHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class ORBConnection {
    private int port;
    private String hostname;
    private ORB orb;
    private NamingContextExt namingContextExt;

    public ORBConnection(int port, String hostname){
        this.port = port;
        this.hostname = hostname;
    }

    public void start() throws InvalidName {
        this.orb = ORB.init(generateArgs(), null);
        POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));

        this.namingContextExt = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
    }

    public PlayerService retrievePlayerRequestStub() throws org.omg.CosNaming.NamingContextPackage.InvalidName, CannotProceed, NotFound {
        return PlayerServiceHelper.narrow(namingContextExt.resolve_str("PlayerService"));
    }

    public GameService retrieveGameService() throws org.omg.CosNaming.NamingContextPackage.InvalidName, CannotProceed, NotFound {
        return GameServiceHelper.narrow(namingContextExt.resolve_str("GameService"));
    }

    private String[] generateArgs(){
        String[] arguments = new String[4];
        arguments[0] = "ORBInitialPort";
        arguments[1] = String.valueOf(port);

        arguments[2] = "ORBInitialHost";
        arguments[3] = hostname;

        return arguments;
    }
}
