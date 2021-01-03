from config import Config
from modules.model import Model
import requests


class Broker:

    def __init__(self, config: Config, model: Model):
        self.config = config
        self.model = model

    def send(self, data: dict):
        url = self.config.get_server_url() + self.model.get_endpoint()
        response = requests.post(url, json=data)

