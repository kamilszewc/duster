import time
from sys import argv
from config import Config
from broker import Broker
from collector import Collector
from modules.registry import registry


if __name__ == '__main__':

    config = Config(argv[1])

    models = []

    # Get all active modules
    sensors = config.get_list_of_sensors()
    for sensor in sensors:
        try:
            models.append(registry["sensors"][sensor])
        except:
            print("There is no registered sensor " + sensor)

    models = [model(config) for model in models]
    brokers = [Broker(config, model) for model in models]
    collectors = [Collector(config, broker) for broker in brokers]
    while True:
        for collector in collectors:
            collector.collect()
            time.sleep(3)
        time.sleep(config.get_sampling_period())
