
class Config(object):
    # General Flask
    DEBUG = False
    TESTING = False
    PORT = 8080

    # Database
    DATABASE = 'sqlite:///../db/concentration.sqlite'  # Database engine and name
