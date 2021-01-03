from abc import ABC, abstractmethod, abstractstaticmethod


class Model(ABC):

    @abstractstaticmethod
    def get_name(self) -> str:
        pass

    @abstractstaticmethod
    def get_endpoint(self) -> str:
        pass

    @abstractmethod
    def get_data(self):
        pass
