from modules.sensors.hm3301 import Hm3301
from modules.sensors.hm3301dummy import Hm3301Dummy
from modules.sensors.bme280 import Bme280
from modules.sensors.bme280dummy import Bme280Dummy

registry = {
    "sensors": {
        "hm3301": Hm3301,
        "hm3301Dummy": Hm3301Dummy,
        "bme280": Bme280,
        "bme280Dummy": Bme280Dummy
    }
}