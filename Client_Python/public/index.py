# Assets shared with members in other packages

import os

os.environ['ORBInitRef'] = 'NameService=corbaloc::corbaserver:2121/NameService'
import sys
from omniORB import CORBA
import CosNaming, PortableServer
from org.amalgam import Service  # REQUIRED


class ORBConnection:
    player_service_stub = None
    game_service_stub = None
    poa = None

    def __init__(self):
        orb = CORBA.ORB_init(sys.argv, CORBA.ORB_ID)
        poa_manager = orb.resolve_initial_references("RootPOA")
        self.poa = poa_manager._narrow(PortableServer.POA)
        self.poa.the_POAManager.activate()
        root_nce = orb.resolve_initial_references("NameService")
        child_nce = root_nce._narrow(CosNaming.NamingContextExt)
        self.player_service_stub = child_nce.resolve_str("PlayerService")
        self.game_service_stub = child_nce.resolve_str("GameService")

    pass


if __name__ == "__main__":
    orb = ORBConnection()
    print(dir(orb.player_service_stub))
    print(dir(orb.game_service_stub))
    pass
