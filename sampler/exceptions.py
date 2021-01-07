
class WrongResponseException(Exception):

    def __init__(self, message="Senor wrong response."):
        self.message = message