import org.amalgam.ControllerException.InvalidRequestException;
import org.amalgam.Service.GameServiceModule.GameService;
import org.amalgam.Service.GameServiceModule.GameServiceHelper;
import org.amalgam.Service.PlayerServiceModule.PlayerService;
import org.amalgam.Service.PlayerServiceModule.PlayerServiceHelper;
import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.UIControllers.PlayerCallbackHelper;
import org.amalgam.UIControllers.PlayerCallbackPOA;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;
import org.amalgam.backend.referenceobjects.Player;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class ConnectionTest {

    private static PlayerService playerServiceStub;
    private static GameService gameServiceStub;

    public static void main(String[] args)  {
        try{
            ORB orb = ORB.init(generateArgs(2018, "localhost"), null);
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();

            NamingContextExt namingReference = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));

            CallbackImpl callback = new CallbackImpl();
            callback.username("Marven");

            playerServiceStub = PlayerServiceHelper.narrow(namingReference.resolve_str("PlayerService"));
            System.out.println("Stub retrieved");
            playerServiceStub.login(PlayerCallbackHelper.narrow(rootPOA.servant_to_reference(callback)), "password123");
            System.out.println("Logged In");

            playerServiceStub.logout(callback.username());
            System.out.println("Logged Out");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static String[] generateArgs(int port, String localhost){
        String[] arguments = new String[4];
        arguments[0] = "-ORBInitialPort";
        arguments[1] = String.valueOf(port);
        arguments[2] = "-ORBInitialHost";
        arguments[3] = localhost;
        System.out.println(localhost);
        return arguments;
    }
}
