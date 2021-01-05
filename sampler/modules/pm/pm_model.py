from abc import ABC

from modules.model import Model


class PmModel(Model, ABC):

    @staticmethod
    def get_endpoint():
        return "/api/v1/record/pm"