from collections import deque


class PlayerGameDetail:
    def __init__(self, username):
        self._username = username
        self._points = 0
        self._words = deque()
        self._duped_words = deque()
        self._ready = False

    # Getter methods
    def get_duped_words(self):
        return self._duped_words

    def get_username(self):
        return self._username

    def get_points(self):
        return self._points

    def get_words(self):
        return self._words

    def is_ready(self):
        return self._ready

    # Setter methods
    def set_points(self, points):
        self._points = points

    def set_ready(self, ready):
        self._ready = ready

    # Functions
    def add_word(self, word):
        self._words.append(word)

    def add_duped_word(self, word):
        self._duped_words.append(word)

    def list_of_words_contains(self, word):
        return word in self._words
