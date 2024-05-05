package org.amalgam.lexilogicserver.Server;
import org.amalgam.DAL.DALPlayer.PlayerDAL;
import org.amalgam.DAL.DALPlayer.PlayerDALHelper;
import org.amalgam.Service.Game.GameService;
import org.amalgam.Service.Game.GameServiceHelper;
import org.amalgam.lexilogicserver.model.DALImpl.PlayerDALImpl;
import org.amalgam.lexilogicserver.model.ServiceImpl.GameServiceImpl;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.*;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class Server {
	public static void main(String[] args) {
		try {
			ORB orb = ORB.init(args, null);

			// get reference to rootpoa & activate the POAManager
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();

			GameServiceImpl gameService = new GameServiceImpl();

			// get object reference from the servant
			org.omg.CORBA.Object ref =
					rootpoa.servant_to_reference(gameService);
			GameService href = GameServiceHelper.narrow(ref);
			// get the root naming context
			org.omg.CORBA.Object objRef =
					orb.resolve_initial_references("NameService");

			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			String name = "Player";
			NameComponent path[] = ncRef.to_name(name);
			ncRef.rebind(path, href);

			System.out.println("Server ready and waiting...");

			orb.run();
		}
		catch(Exception rException){
				rException.printStackTrace();
		}
	}
}
