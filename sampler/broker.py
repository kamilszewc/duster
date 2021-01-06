from config import Config
from modules.model import Model
import requests


class Broker:

    def __init__(self, config: Config, model: Model):
        self.config = config
        self.model = model

    def send(self, data_to_send: dict):
        for key in data_to_send:
            data = data_to_send[key]
            try:
                server = self.config.get_server_url()
            except:
                server = "http://localhost"
            url = server + Model.endpoints[key]
            print(url)
            response = requests.post(url, json=data)

