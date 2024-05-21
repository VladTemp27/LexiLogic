import org.amalgam.Service.GameServiceModule.GameService;
import org.amalgam.Service.GameServiceModule.GameServiceHelper;
import org.amalgam.Service.PlayerServiceModule.PlayerService;
import org.amalgam.Service.PlayerServiceModule.PlayerServiceHelper;
import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.Utils.Exceptions.MatchCreationFailedException;
import org.amalgam.lexilogicserver.model.handler.GameHandler.GameRoom;
import org.amalgam.lexilogicserver.model.handler.GameHandler.GameRoomResponseBuilder;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.PlayerGameDetail;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class MatchMakeTest implements ControllerInterface{
    private PlayerService playerService;
    private GameService gameService;
    private POA rootPOA;
    private CallbackImpl callback;

    private static Scanner kInput = new Scanner(System.in);

    public static void main(String[] args) throws WrongPolicy, ServantNotActive, MatchCreationFailedException {
        MatchMakeTest program = new MatchMakeTest();
        program.run(program);

    }

    public void run (MatchMakeTest program) throws WrongPolicy, ServantNotActive, MatchCreationFailedException {
        program.getAllStubs();
        testGameRoomResponseBuilder();
//        System.out.print("Enter username: ");
//        String user = kInput.nextLine();
//
//        program.callback = new CallbackImpl();
//        program.callback.username(user);
//        program.callback.setController(this);
//
//
//        String response = program.gameService.matchMake(PlayerCallbackHelper.narrow(program.rootPOA.servant_to_reference(program.callback)));
//        System.out.println(response);
    }

    public void getAllStubs(){
        try {
            ORB orb = ORB.init(generateArguments(2018, "localhost"), null);
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

    private void testGameRoomResponseBuilder() {
        try {
            LinkedHashMap<String, PlayerGameDetail> details = new LinkedHashMap<>();
            details.put("player1", new PlayerGameDetail("player1"));
            details.put("player2", new PlayerGameDetail("player2"));

            LinkedHashMap<String, PlayerCallback> playerCallbacks = new LinkedHashMap<>();

            details.get("player1").setPoints(0);
            details.get("player2").setPoints(0);

            GameRoom gameRoom = new GameRoom(123, details, playerCallbacks, 60);

            gameRoom.stagePlayers();

            String gameStartedResponse = GameRoomResponseBuilder.buildGameStartedResponse(gameRoom);
            System.out.println("Game Started Response:");
            System.out.println(gameStartedResponse);

            String stagePlayersResponse = GameRoomResponseBuilder.buildStagePlayersResponse(gameRoom, 10);
            System.out.println("Stage Players Response:");
            System.out.println(stagePlayersResponse);

            String winnerResponse = GameRoomResponseBuilder.buildWinnerResponse("player1");
            System.out.println("Winner Response:");
            System.out.println(winnerResponse);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
