FROM ubuntu:focal
RUN apt-get update
RUN apt-get install -y python3 python3-pip python3-pigpio pigpio-tools gunicorn git vim net-tools
COPY requirements.txt /app/
RUN pip3 install -r /app/requirements.txt
COPY *.py /app/
