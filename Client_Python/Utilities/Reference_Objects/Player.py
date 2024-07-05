class Player:
    def __init__(self, userID, username, password, lastLoggedIn):
        self.userID = userID
        self.username = username
        self.password = password
        self.lastLoggedIn = lastLoggedIn

    @property
    def userID(self):
        return self._userID

    @userID.setter
    def userID(self, userID):
        self._userID = userID

    @property
    def username(self):
        return self._username

    @username.setter
    def username(self, username):
        self._username = username

    @property
    def password(self):
        return self._password

    @password.setter
    def password(self, password):
        self._password = password

    @property
    def lastLoggedIn(self):
        return self._lastLoggedIn

    @lastLoggedIn.setter
    def lastLoggedIn(self, lastLoggedIn):
        self._lastLoggedIn = lastLoggedIn


