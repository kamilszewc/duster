import time
from modules.pm.pm_model import PmModel
try:
    import pigpio
except:
    pass


class Hm3301WrongResponseException(Exception):

    def __init__(self, message="Hm3301 wrong response."):
        self.message = message


class Hm3301(PmModel):

    @staticmethod
    def get_name(self):
        return "hm3301"

    def __init__(self, config, pi):
        self.config = config
        self.pi = pi

        sensor_parameters = self.config.get_sensor_parameters('pm', 'hm3301')

        try:
            self.sda = int(sensor_parameters['pigpio']['sda'])
        except:
            self.sda = 2

        try:
            self.scl = int(sensor_parameters['pigpio']['scl'])
        except:
            self.scl = 3

        try:
            self.i2c_address = hex(sensor_parameters['pigpio']['i2c'])
            print(self.i2c_addres)
        except:
            self.i2c_address = 0x40

        try:
            self.pi.bb_i2c_close(self.sda)
        except:
            pass

        self.pi.set_pull_up_down(self.sda, pigpio.PUD_UP)
        self.pi.set_pull_up_down(self.scl, pigpio.PUD_UP)
        self.pi.bb_i2c_open(self.sda, self.scl, 20000)

        self.pi.bb_i2c_zip(self.sda, [4, self.i2c_address, 2, 7, 1, 0x80, 2, 7, 1, 0x88, 3, 0])
        time.sleep(5)

    def close(self):
        self.pi.bb_i2c_close(self.sda)
        self.pi.stop()

    @staticmethod
    def checksum(data):
        chksum = 0
        for i in range(28):
            chksum += data[i]
        chksum = chksum & 0xff
        return chksum == data[28]

    def get_data(self):
        (count, data) = self.pi.bb_i2c_zip(self.sda, [4, self.i2c_address, 2, 7, 1, 0x81, 3, 2, 6, 29, 3, 0])
        data = list(data)

        if self.checksum(data):
            concentration = {
                'pm10': data[10] << 8 | data[11],
                'pm25': data[12] << 8 | data[13],
                'pm100': data[14] << 8 | data[15],
                # 'pm1 factory': data[4] << 8 | data[5],
                # 'pm2.5 factory': data[6] << 8 | data[7],
                # 'pm10 factory': data[8] << 8 | data[9],
            }
            return concentration
        else:
            raise Hm3301WrongResponseException

