import org.amalgam.Service.GameServiceModule.GameService;
import org.amalgam.Service.GameServiceModule.GameServiceHelper;
import org.amalgam.Service.PlayerServiceModule.PlayerService;
import org.amalgam.Service.PlayerServiceModule.PlayerServiceHelper;
import org.amalgam.UIControllers.PlayerCallbackHelper;
import org.amalgam.Utils.Exceptions.MatchCreationFailedException;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import java.util.Scanner;

public class MatchMakeTest implements ControllerInterface{
    private PlayerService playerService;
    private GameService gameService;
    private POA rootPOA;
    private CallbackImpl callback;

    private static Scanner kInput = new Scanner(System.in);

    public static void main(String[] args) throws WrongPolicy, ServantNotActive, MatchCreationFailedException {
        MatchMakeTest program = new MatchMakeTest();
        program.getAllStubs();

        System.out.print("Enter username: ");
        String user = kInput.nextLine();

        program.callback = new CallbackImpl();
        program.callback.username(user);

        program.callback.setController(program);

        String response = program.gameService.matchMake(PlayerCallbackHelper.narrow(program.rootPOA.servant_to_reference(program.callback)));
        System.out.println(response);
    }

    public void getAllStubs(){
        try {
            ORB orb = ORB.init(generateArguments(2020, "localhost"), null);
            rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();

            NamingContextExt nameService = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));

            playerService = PlayerServiceHelper.narrow(nameService.resolve_str("PlayerService"));
            gameService = GameServiceHelper.narrow(nameService.resolve_str("GameService"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private String[] generateArguments(int port, String hostname){
        String[] args = new String[4];

        args[0] = "-ORBInitialPort";
        args[1] = String.valueOf(port);

        args[2] = "-ORBInitialHost";
        args[3] = hostname;

        return args;
    }

    @Override
    public void testUICall(String jsonString) {
        System.out.println(jsonString);
    }
}
