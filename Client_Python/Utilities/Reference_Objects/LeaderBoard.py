class LeaderBoard:
    def __init__(self, leader_board_id=None, username=None, points=None, rank=None):
        self._leader_board_id = leader_board_id
        self._username = username
        self._points = points
        self._rank = rank

    # Getter
    def get_leader_board_id(self):
        return self._leader_board_id

    def get_username(self):
        return self._username

    def get_points(self):
        return self._points

    def get_rank(self):
        return self._rank

    # Setter
    def set_leader_board_id(self, value):
        self._leader_board_id = value

    def set_username(self, value):
        self._username = value

    def set_points(self, value):
        self._points = value

    def set_rank(self, value):
        self._rank = value