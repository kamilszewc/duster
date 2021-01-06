import random
from modules.model import Model


class Hm3301Dummy(Model):

    @staticmethod
    def get_name(self):
        return "hm3301Dummy"

    def __init__(self, config):
        self.config = config

    def get_data(self):
        concentration = {
            'pm10': random.randint(a=1, b=100),
            'pm25': random.randint(a=1, b=100),
            'pm100': random.randint(a=1, b=100)
        }
        return concentration

    def get_data_to_send(self):
        return {
            "pm": self.get_data()
        }
