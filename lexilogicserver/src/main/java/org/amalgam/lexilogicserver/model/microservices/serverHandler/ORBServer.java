package org.amalgam.lexilogicserver.model.microservices.serverHandler;

import org.amalgam.Service.GameServiceModule.GameService;
import org.amalgam.Service.GameServiceModule.GameServiceHelper;
import org.amalgam.Service.PlayerServiceModule.PlayerService;
import org.amalgam.Service.PlayerServiceModule.PlayerServiceHelper;
import org.amalgam.lexilogicserver.model.microservices.wordbox.Exceptions.ReadFailure;
import org.amalgam.lexilogicserver.model.microservices.wordbox.Reader;
import org.amalgam.lexilogicserver.model.serviceimpl.GameServiceImpl;
import org.amalgam.lexilogicserver.model.serviceimpl.PlayerServiceImpl;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import java.io.FileNotFoundException;

public class ORBServer implements Runnable{
    private int port;
    private String hostname;
    private String[] arguments;
    private ORBServerCallback callback;

    public ORBServer(ORBServerCallback callback,int port, String hostname){
        this.port = port;
        this.hostname = hostname;
        this.callback = callback;
        generateServerArguments(this.port, this.hostname);
    }
    @Override
    public void run() {
        try {
            ORB orb = ORB.init(arguments, null);
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();

            Reader reader = new Reader("lexilogicserver/src/main/java/org/amalgam/lexilogicserver/model/microservices" +
                    "/wordbox/words.txt");

            GameServiceImpl gameServiceServant = new GameServiceImpl(reader.retrieveListOfWords(false));
            GameService gameServiceReference = GameServiceHelper.narrow(rootPOA.servant_to_reference(gameServiceServant));

            PlayerServiceImpl playerServiceServant = new PlayerServiceImpl();
            PlayerService playerServiceReference = PlayerServiceHelper.narrow(rootPOA.servant_to_reference(playerServiceServant));

            NamingContextExt nameService = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));

            NameComponent[] playerServicePath = nameService.to_name("PlayerService");
            NameComponent[] gameServicePath = nameService.to_name("GameService");

            nameService.rebind(gameServicePath, gameServiceReference);
            nameService.rebind(playerServicePath, playerServiceReference);
            callback.notifyServantsBinded();

            orb.run();
            callback.notifyServerShutdown();
        }catch (InvalidName | AdapterInactive | WrongPolicy | ServantNotActive e) {
            throw new RuntimeException(e);
        } catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
            throw new RuntimeException(e);
        } catch (CannotProceed e) {
            throw new RuntimeException(e);
        } catch (NotFound e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ReadFailure e) {
            throw new RuntimeException(e);
        }
    }

    public void generateServerArguments(int port, String hostname){
        arguments = new String[4];

        //-ORBInitialPort <port>
        arguments[0] = "-ORBInitialPort";
        arguments[1] = String.valueOf(port);

        //-ORBInitialHost <hostname>
        arguments[2] = "-ORBInitialHost";
        arguments[3] = hostname;
    }
}
