import random
import time
import datetime
from hm3301 import Hm3301WrongResponseException


class Hm3301Dummy:

    def __init__(self, pi, sda=2, scl=3, i2c_address=0x40):
        pass

    def close(self):
        pass

    def get_data(self):
        concentration = {
            'pm10': random.randint(a=1, b=100),
            'pm25': random.randint(a=1, b=100),
            'pm100': random.randint(a=1, b=100)
        }
        return concentration


if __name__ == '__main__':

    hm3301 = Hm3301Dummy(None)

    try:
        while True:
            data = hm3301.get_data()
            print(data)

            time.sleep(5)
    except Hm3301WrongResponseException as ex:
        print(ex.message)
    finally:
        hm3301.close()
