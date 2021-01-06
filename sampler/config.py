import yaml


class Config(object):

    def __init__(self, config_file):

        with open(config_file, 'r') as stream:
            try:
                self.config = yaml.safe_load(stream)['sampler']
            except yaml.YAMLError as ex:
                print(ex)

    def get_server_url(self):
        try:
            period = self.config['server-url']
        except:
            period = "http://localhost:8080"
        return period

    def get_sampling_period(self):
        try:
            period = self.config['period']
        except:
            period = 120
        return period

    def get_list_of_sensors(self) -> list:
        list_of_sensors = [it['name'] for it in self.config['sensors']]
        return list_of_sensors

    def get_sensor_parameters(self, sensor_name: str) -> dict:
        pass
        #    for sensor in self.config['sensors']:
    #        if sensor['name'] == sensor_name:
    #        return sensor





