import org.amalgam.Service.GameServiceModule.GameService;
import org.amalgam.Service.GameServiceModule.GameServiceHelper;
import org.amalgam.Service.PlayerServiceModule.PlayerService;
import org.amalgam.Service.PlayerServiceModule.PlayerServiceHelper;
import org.amalgam.lexilogicserver.model.serviceimpl.GameServiceImpl;
import org.amalgam.lexilogicserver.model.serviceimpl.PlayerServiceImpl;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class SimpleServer {
    public static void main(String[] args) {
        try{
            ORB orb = ORB.init(args, null);

            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();

            NamingContextExt nameService = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
            GameServiceImpl gameService = new GameServiceImpl();
            PlayerServiceImpl playerService = new PlayerServiceImpl();

            NameComponent gamePath[] = nameService.to_name("GameService");
            NameComponent playerPath[] = nameService.to_name("PlayerService");

            GameService gameRef = GameServiceHelper.narrow(rootPOA.servant_to_reference(gameService));
            PlayerService playerRef = PlayerServiceHelper.narrow(rootPOA.servant_to_reference(playerService));

            nameService.rebind(gamePath, gameRef);
            nameService.rebind(playerPath,playerRef);

            orb.run();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
