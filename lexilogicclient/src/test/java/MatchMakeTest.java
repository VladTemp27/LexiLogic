import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.amalgam.Service.GameServiceModule.GameService;
import org.amalgam.Service.GameServiceModule.GameServiceHelper;
import org.amalgam.Service.PlayerServiceModule.PlayerService;
import org.amalgam.Service.PlayerServiceModule.PlayerServiceHelper;
import org.amalgam.UIControllers.PlayerCallbackHelper;
import org.amalgam.Utils.Exceptions.DuplicateWordException;
import org.amalgam.Utils.Exceptions.InvalidWordFormatException;
import org.amalgam.Utils.Exceptions.MatchCreationFailedException;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import java.util.Scanner;

public class MatchMakeTest implements ControllerInterface {
    private PlayerService playerService;
    private GameService gameService;
    private POA rootPOA;
    private CallbackImpl callback;
    private int gameRoomID;
    private String currentState = "";
    private String user;
    private boolean gameValid = true;

    private static Scanner kInput = new Scanner(System.in);

    public static void main(String[] args) throws WrongPolicy, ServantNotActive, MatchCreationFailedException, DuplicateWordException, InvalidWordFormatException {
        MatchMakeTest program = new MatchMakeTest();
        program.getAllStubs();

        //Prompts user for mock username
        System.out.print("Enter username: ");
        program.user = kInput.nextLine();

        //Creates a new playercallback
        program.callback = new CallbackImpl();
        program.callback.username(program.user);

        //Sets this as the controller
        program.callback.setController(program);

        //Sends matchmake request from server and waits for a response
        String response = program.gameService.matchMake(PlayerCallbackHelper.narrow(program.rootPOA.servant_to_reference(program.callback)));
        System.out.println("SERVER RESPONSE:");
        System.out.println(response);

        //Performs a handshake via gameService with the corresponding gameroom using the id from the response of
        // matchmake. This is important, it is needed to start communications between game room and client without
        // this, players would not be staged.
        System.out.println("Sending handshake");
        program.gameService.readyHandshake(program.user, program.getIDFromResponse(response));

        //states will then be handled by the callback method check line 101


        //Game logic below
        while(!program.currentState.equals("game_ended")){
            String word = "";
            word = kInput.nextLine();
            program.gameService.verifyWord(word, program.user, program.gameRoomID);
        }

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
//        System.out.println(jsonString);
//        JsonElement rootElement = JsonParser.parseString(jsonString);
//        JsonObject rootObject = rootElement.getAsJsonObject();
//        gameRoomID = rootObject.get("room_id").getAsInt();
//        currentState = rootObject.get("state").getAsString();
//        System.out.println(gameRoomID);

        JsonElement rootElement = JsonParser.parseString(jsonString);
        JsonObject rootObject = rootElement.getAsJsonObject();
        currentState = rootObject.get("state").getAsString();
        gameRoomID = rootObject.get("room_id").getAsInt();

        if(currentState.equals("staging")){
            System.out.println("staging");
            //stagingStateHandler();
            System.out.println("sending read...");
            System.out.println(this.user+" "+gameRoomID);
            gameService.playerReady(user, gameRoomID);

            return;
        }

        if(currentState.equals("game_started")){
            gameStartedHandler();
            System.out.println("simulating game");
            return;
        }

        if(currentState.equals("invalid_word")){
            System.out.println("Invalid Word");
        }


    }

    public void stagingStateHandler(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void gameStartedHandler(){
        try{
            //Thread.sleep(000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private int getIDFromResponse(String response){
        JsonElement rootElement = JsonParser.parseString(response);
        JsonObject rootObject = rootElement.getAsJsonObject();
        return rootObject.get("gameRoomID").getAsInt();
    }
}
