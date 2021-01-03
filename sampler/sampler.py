import os
import time
from sys import argv
from config import Config
from broker import Broker
from collector import Collector
from modules.pm.hm3301dummy import Hm3301Dummy
from modules.registry import registry

try:
    import pigpio
except:
    pass


if __name__ == '__main__':

    config = Config(argv[1])

    try:
        pi = pigpio.pi(os.environ['PIGPIOD_HOST'])
    except KeyError:
        pi = pigpio.pi()
    except:
        pi = None

    models = []

    # Get all active modules
    modules = config.get_list_of_modules()
    for module in modules:
        # Get all sensors
        sensors = config.get_list_of_sensors(module)
        for sensor in sensors:
            try:
                models.append(registry[module][sensor])
            except:
                print("There is no registered sensor " + sensor)

    models = [model(config, pi) for model in models]
    brokers = [Broker(config, model) for model in models]
    collectors = [Collector(config, broker) for broker in brokers]
    while True:
        for collector in collectors:
            collector.collect()
        time.sleep(config.get_sampling_period())
