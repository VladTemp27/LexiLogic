import os
from public.index import ORBConnection
from player_callback_impl import PlayerCallbackImpl

CURRENT_USER = {
    'username': None,
    'password': None,
    'player_callback': None,
    'player_callback_impl': None
}

orb: ORBConnection = ORBConnection()

def login_view():
    global CURRENT_USER
    while True:
        os.system('cls||clr')
        print("***** Log-In ****")
        username = input("Username: ")
        password = input("Password: ")
        if authenticate(username, password):
            CURRENT_USER['username'] = username
            CURRENT_USER['password'] = password
            break

    os.environ['username'] = username

    return True


def authenticate(username: str, password: str):
    global CURRENT_USER
    global orb
    print(dir(orb.game_service_stub))
    print(dir(orb.player_service_stub))
    servant_player_callback = PlayerCallbackImpl()
    servant_player_callback.username = username
    CURRENT_USER['player_callback_impl'] = servant_player_callback
    CURRENT_USER['player_callback'] = orb.poa.servant_to_reference(servant_player_callback)
    try:
        orb.player_service_stub.login(CURRENT_USER['player_callback'], password)
    except Exception as e:
        print(e)
    else:
        return True
