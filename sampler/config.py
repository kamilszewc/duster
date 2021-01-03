import yaml


class Config(object):

    def __init__(self, config_file):

        with open(config_file, 'r') as stream:
            try:
                self.config = yaml.safe_load(stream)['sampler']
            except yaml.YAMLError as ex:
                print(ex)

    def get_sampling_period(self):
        try:
            period = self.config['period']
        except:
            period = 120
        return period

    def get_server_url(self):
        try:
            url = self.config['pigpio']['url']
        except:
            url = "http://localhost"
        return url

    def get_list_of_modules(self):
        list_of_modules = [it for it in self.config['sensors']]
        return list_of_modules

    def get_list_of_sensors(self, module_name: str) -> list:
        list_of_sensors = [it['name'] for it in self.config['sensors'][module_name]]
        return list_of_sensors

    def get_sensor_parameters(self, module_name: str, sensor_name: str) -> dict:
        for sensor in self.config['sensors'][module_name]:
            if sensor['name'] == sensor_name:
                return sensor





