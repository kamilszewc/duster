from abc import ABC, abstractmethod, abstractstaticmethod


class Model(ABC):

    endpoints = {
        "pm": "/api/v1/record/pm",
        "temperature": "/api/v1/record/temperature",
        "pressure": "/api/v1/record/pressure",
        "humidity": "/api/v1/record/humidity"
    }

    @abstractstaticmethod
    def get_name(self) -> str:
        pass

    @abstractmethod
    def get_data_to_send(self):
        pass
