package org.amalgam.lexilogicclient;

import org.amalgam.DAL.DALPlayer.PlayerDAL;
import org.amalgam.DAL.DALPlayer.PlayerDALHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

public class Client {
	static PlayerDAL playerImpl;
	public static void main(String[] args) {
		try {
// create and initialize the ORB
			ORB orb = ORB.init(args, null);
// get the root naming context
			org.omg.CORBA.Object objRef =
					orb.resolve_initial_references("NameService");
// Use NamingContextExt instead of NamingContext. This is part
// of the Interoperable naming Service.
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
// resolve the Object Reference in Naming
			String name = "Player";

			playerImpl = PlayerDALHelper.narrow(ncRef.resolve_str(name));
			playerImpl.insertNewPlayer("test2", "3", "2024-04-30");
			System.out.println("Success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
