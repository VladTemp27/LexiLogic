import org__POA
import org
from update_dispatcher import UpdateDispatcher


class PlayerCallbackImpl(org__POA.amalgam.UIControllers.PlayerCallback):
    username = ""
    update_dispatch = None

    def __init__(self): pass

    def get_username(self):
        return self.username

    def set_username(self, new_username):
        self.username = new_username

    def uiCall(self, json_string):
        self.update_dispatch.update(json_string)

    def controller_interface(self, update_dispatch):
        self.update_dispatch = update_dispatch


if __name__ == "__main__":
    player_callback_impl = PlayerCallbackImpl()
    print(dir(player_callback_impl))
    print(dir(org.amalgam.UIControllers.PlayerCallback))
    print(dir(org__POA.amalgam.UIControllers.PlayerCallback))
