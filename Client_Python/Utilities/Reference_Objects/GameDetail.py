class GameDetail:
    def __init__(self, username=None, lobby_id=None, total_points=None):
        self._username = username
        self._lobby_id = lobby_id
        self._total_points = total_points

    def get_username(self):
        return self._username

    def set_username(self, value):
        self._username = value

    def get_lobby_id(self):
        return self._lobby_id

    def set_lobby_id(self, value):
        self._lobby_id = value

    def get_total_points(self):
        return self._total_points

    def set_total_points(self, value):
        self._total_points = value