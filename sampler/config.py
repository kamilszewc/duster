
class Config(object):

    # SERVER
    SERVER_URL = "http://localhost"

    # Data sampling
    SAMPLING_STANDALONE = True      # If true, standalone sampler needs to be launched
    SAMPLING_TIME = 60             # In seconds
    SAMPLING_DUMMY = False          # True to turn on randomly generated samples (testing purposes)

    # PI setup
    PI_SDA = 2                  # Sda id
    PI_SCL = 3                  # Scl id
    PI_I2C_ADDRESS = 0x40       # I2c address
