## Duster - Particulate Matter Concentration analyzer - local (home) server for Raspberry Pi

Web app and set of drivers (modules) for Raspberry PI and Grove sensors
This version is tested only in Raspberry PI 4 and hm3301 sensor.

### Requrements

This code needs Python3 (data sampler), Java JDK (web app), pigpod and MariaDB database.
All of them available in Raspbian.

### Installation

The app is composed of two components: python sampler with modules for different sensors, and Spring based web app.
The easiest way to run is to use `duster` script. 
By default, the data is stored in the MariaDB database.
To change the database params, please modify `config.yml`.
If you are not familiar with SQL, please just install MariaDB and type (in Ubuntu):

> sudo mariadb -u root

And then, after the propomt:

> create schema duster;
> 
> create user 'duster'@'localhost' identified by 'dusterPassword';
> 
> grant all privileges on duster.* to 'duster'@'localhost';
> 
> flush privileges;

The duster web app will do the rest.

Before using, you may need to install required python modules:
> pip install -r requrements.txt

#### Connecting Raspberry Pi and sensors.

The connection of Raspberry PI with sensors is straightforward, the I2C bus is used.
There is a lot of web pages describing it.
The driver assumes that SDA at pin 2, SCL at 3, I2C address is 0x40.
These values of `SDA`, `SCL` and `I2C address` can be changed in `config.yml`.

### Running duster release package

Before running `sampler.py`, the pigpiod client need to be run:
> sudo pigpiod

Download duster release package from github repo (please use a correct <release_version>)

> https://github.com/kamilszewc/duster/releases/download/<release_version>/duster.zip

Unzip it, make the needed changes in `config.yml` and call:

> ./dusterRun config.yml

### Running duster from sources

Before running `sampler.py`, the pigpiod client need to be run:

> sudo pigpiod

After this, please make the needed changes in `config.yml` and call:

> ./dusterRun config.yml

#### Running sampler.py standalone

Before running `sampler.py`, the pigpiod client need to be run:
> sudo pigpiod

Then, sampler can be run using simply (from `sampler` directory):
> python3 sampler.py config.yml

#### Running webapp.py standalone

The web application can be run just by calling (from `dusterapp` directory):
> ./gradlew bootRun --args="--spring-config.location=config.yml"

or alternatively:

> ./gradlew build
> 
> java -jar build/libs/*.jar --spring.config.location=config.yml

### Duster as a service

In order to make your duster running as a service in you Raspberry,
there are two service files provided: `pigpiod.service` and `duster.service`.
Please open them and set correct paths to your duster app.
Then, all you need to copy them to `/etc/systemd/system`, and call:

> sudo systemctl start pigpiod.service
> 
> sudo systemctl enable pigpiod.service
> 
> sudo systemctl start duster.service
> 
> sudo systemctl enable duster.service

### Acknowladgements

The project is served under GPL3 license.

For plotting, the Chart.js library is used (MIT License): https://www.chartjs.org/

The driver has been inspired by https://github.com/switchdoclabs/SDL_Pi_HM3301 project. 
Please also look at their great article here: https://www.switchdoc.com/2020/02/tutorial-air-quality-on-the-raspberry-pi-with-the-hm3301/

