import os
import time
from hm3301 import Hm3301WrongResponseException, Hm3301
from config import Config
from hm3301dummy import Hm3301Dummy
import requests

try:
    import pigpio
except:
    pass


class Sampler(object):

    def __init__(self, sensor):
        self.sensor = sensor

    def submit(self):

        url = Config.SERVER_URL + "/api/v1/recordPmMeasurement"

        while True:
            try:
                data = self.sensor.get_data()
                print(data)

                response = requests.post(url, json=data)

                time.sleep(Config.SAMPLING_TIME)
            except Hm3301WrongResponseException as ex:
                print(ex.message)
            finally:
               self.sensor.close()


if __name__ == '__main__':

    if not Config.SAMPLING_DUMMY:
        try:
            pi = pigpio.pi(os.environ['PIGPIOD_HOST'])
        except KeyError:
            pi = pigpio.pi()

        hm3301 = Hm3301(pi, sda=Config.PI_SDA, scl=Config.PI_SCL, i2c_address=Config.PI_I2C_ADDRESS)
    else:
        hm3301 = Hm3301Dummy(None)

    sampler = Sampler(hm3301)
    sampler.collect()
