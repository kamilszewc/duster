## Duster - Particulate Matter Concentration analyzer

Driver and simple web app application for Raspberry PI and Grove hm3301 sensor.
This version is tested only in Raspberry PI 4.

### Requrements

This code needs Python3 (data sampler), Java JDK (web app) and pigpod.
All of them available in Raspbian.

### Installation

The app is composed of three component: the hm3301 driver, simple python sampler, and Spring based web app.
The easiest way to run is to use `duster` script. 
By default, the data is stored in the sqlite3 database (inside db directory).

Before using, you may need to install required python modules:
> pip install -r requrements.txt

#### Connecting Raspberry Pi and hm3301.

The connection of Raspberry PI with hm3301 is straightforward, the I2C bus is used.
The driver assumes that SDA at pin 2, SCL at 3, I2C address is 0x40.
These values can be changed in `sampler/config.py`.

#### Running sampler.py standalone

Before running `sampler.py`, the pigpiod client need to be run:
> sudo pigpiod

Then, sampler can be run using simply (from `sampler` directory):
> python3 sampler.py

#### Running webapp.py standalone

The web application can be run just by calling (from `dusterapp` directory):
> ./gradlew bootRun --args="--server.port=(port)"

It serves the application by default server in port 8080.
It can be changed by adding `--args="--server.port=(port)"` parameter.

### Acknowladgements

The project is served under GPL3 license.

For plotting, the Chart.js library is used (MIT License): https://www.chartjs.org/

The driver has been inspired by https://github.com/switchdoclabs/SDL_Pi_HM3301 project. 
Please also look at their great article here: https://www.switchdoc.com/2020/02/tutorial-air-quality-on-the-raspberry-pi-with-the-hm3301/

