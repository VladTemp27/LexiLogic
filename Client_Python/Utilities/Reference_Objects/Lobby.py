class Lobby:
    def __init__(self, lobby_id=None, winner=None, winner_score=None):
        self._lobby_id = lobby_id
        self._winner = winner
        self._winner_score = winner_score

    # Getter
    def get_lobby_id(self):
        return self._lobby_id

    def get_winner(self):
        return self._winner

    # Setter
    def set_lobby_id(self, value):
        self._lobby_id = value

    def set_winner(self, value):
        self._winner = value

    def set_winner_score(self, value):
        self._winner_score = value

    def get_winner_score(self):
        return self._winner_score
