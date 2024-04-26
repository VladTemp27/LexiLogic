package org.amalgam.lexilogicserver.Server;
import org.amalgam.lexilogicserver.DAL.DALPlayer.PlayerDAL;
import org.amalgam.lexilogicserver.DAL.DALPlayer.PlayerDALHelper;
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

			Servant servant = new Servant();
//			orb.connect((Object) servant);
			// get object reference from the servant
			org.omg.CORBA.Object ref =
					rootpoa.servant_to_reference(servant);
			PlayerDAL href = PlayerDALHelper.narrow(ref);
//			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
//			NamingContext ncRef = NamingContextHelper.narrow(objRef);
			// get the root naming context
			org.omg.CORBA.Object objRef =
					orb.resolve_initial_references("NameService");

			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			String name = "Player";
//			NameComponent nc = new NameComponent("AgesDB", "");
//			NameComponent path[] = {nc};
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
