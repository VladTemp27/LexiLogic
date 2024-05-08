package org.amalgam.lexilogicserver.Server;

import org.amalgam.Service.GameServiceModule.GameService;
import org.amalgam.Service.GameServiceModule.GameServiceHelper;
import org.amalgam.Service.PlayerServiceModule.PlayerService;
import org.amalgam.Service.PlayerServiceModule.PlayerServiceHelper;
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

public class Server implements Runnable {

	private int port;
	private String hostname;

	public Server(int port, String hostname){
		this.port = port;
		this.hostname = hostname;
	}
	public void run() {
		try {
			ORB orb = ORB.init(generateArgs(), null);

			POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootPOA.the_POAManager().activate();

			NamingContextExt namingServiceRef = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));

			//Generates the servant of player service from impl
			PlayerServiceImpl playerServiceServant = new PlayerServiceImpl();
			//Inserts the servant into an object of the interface using rootPOA, to be given to the orb
			PlayerService playerServiceRef = PlayerServiceHelper.narrow(rootPOA.servant_to_reference(playerServiceServant));

			//Same process as PlayerService
			GameServiceImpl gameServiceImpl = new GameServiceImpl();
			GameService gameServiceRef = GameServiceHelper.narrow(rootPOA.servant_to_reference(gameServiceImpl));

			String playerServiceBind = "PlayerService";
			String gameServiceBind = "GameService";

			NameComponent[] playerServicePath = namingServiceRef.to_name(playerServiceBind);
			NameComponent[] gameServicePath = namingServiceRef.to_name(gameServiceBind);

			namingServiceRef.rebind(playerServicePath, playerServiceRef);
			namingServiceRef.rebind(gameServicePath, gameServiceRef);

			orb.run();
		}catch (InvalidName e){
			e.printStackTrace();
		} catch (WrongPolicy e) {
			throw new RuntimeException(e);
		} catch (ServantNotActive e) {
			throw new RuntimeException(e);
		} catch (AdapterInactive e) {
			throw new RuntimeException(e);
		} catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
			throw new RuntimeException(e);
		} catch (CannotProceed e) {
			throw new RuntimeException(e);
		} catch (NotFound e) {
			throw new RuntimeException(e);
		}
	}

	//This generates the program arguments such as port and hostname from the actual parameter given form the constructor
	private String[] generateArgs(){
		String[] arguments = new String[4];
		arguments[0] = "ORBInitialPort";
		arguments[1]= String.valueOf(port);

		arguments[2] = "ORBInitialHost";
		arguments[3] = hostname;
		return arguments;
	}
}
