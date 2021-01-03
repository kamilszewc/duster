from modules.pm.hm3301 import Hm3301
from modules.pm.hm3301dummy import Hm3301Dummy

registry = {
    "pm": {
        "hm3301": Hm3301,
        "hm3301Dummy": Hm3301Dummy
    },
    "temperature": {
    }
}