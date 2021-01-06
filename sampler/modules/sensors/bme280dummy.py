#!/usr/bin/python
# --------------------------------------
#    ___  ___  _ ____
#   / _ \/ _ \(_) __/__  __ __
#  / , _/ ___/ /\ \/ _ \/ // /
# /_/|_/_/  /_/___/ .__/\_, /
#                /_/   /___/
#
#           bme280.py
#  Read data from a digital pressure sensor.
#
#  Official datasheet available from :
#  https://www.bosch-sensortec.com/bst/products/all_products/bme280
#
# Author : Matt Hawkins
# Date   : 21/01/2018
#
# https://www.raspberrypi-spy.co.uk/
#
# --------------------------------------
import smbus
import time
from ctypes import c_short

DEVICE = 0x76  # Default device I2C address
BUS = 1 # Rev 2 Pi, Pi 2 & Pi 3 uses bus 1


class Bme280Dummy(object):

    def __init__(self, config):
        self.config = config

    def readBME280All(self, addr=DEVICE):
        return 50.0, 100.0, 200.0

    @staticmethod
    def get_name(self):
        return "bme280"

    def get_data_to_send(self):
        temperature, pressure, humidity = self.readBME280All()
        return {
            "temperature": {"temperature": temperature},
            "pressure": {"pressure": pressure},
            "humidity": {"humidity": humidity}
        }

