import random
from modules.pm.pm_model import PmModel


class Hm3301Dummy(PmModel):

    @staticmethod
    def get_name(self):
        return "hm3301Dummy"

    def __init__(self, config, pi):
        self.config = config
        self.pi = pi

    def close(self):
        pass

    def get_data(self):
        concentration = {
            'pm10': random.randint(a=1, b=100),
            'pm25': random.randint(a=1, b=100),
            'pm100': random.randint(a=1, b=100)
        }
        return concentration