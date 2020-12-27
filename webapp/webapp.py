import datetime
from flask import Flask, render_template
import threading
import sqlalchemy as db
from config import Config
import numpy as np

app = Flask(__name__)
app.config.from_object("config.Config")
engine = db.create_engine(Config.DATABASE)


def parse_data(dataset):
    labels = [str(element['date']).split('.')[0] for element in dataset]
    data = {'pm1 factory': [element['pm1 factory'] for element in dataset],
            'pm2.5 factory': [element['pm2.5 factory'] for element in dataset],
            'pm10 factory': [element['pm10 factory'] for element in dataset],
            'pm1 atmospheric': [element['pm1 atmospheric'] for element in dataset],
            'pm2.5 atmospheric': [element['pm2.5 atmospheric'] for element in dataset],
            'pm10 atmospheric': [element['pm10 atmospheric'] for element in dataset]
            }
    return labels, data


def query_data(hours=0, minutes=0, days=0):
    connection = engine.connect()
    metadata = db.MetaData()
    concentration = db.Table('concentration', metadata, autoload=True, autoload_with=engine)
    query = db.select([concentration])
    query = query.where(concentration.c.date > datetime.datetime.now() - datetime.timedelta(hours=hours, minutes=minutes, days=days))
    result_proxy = connection.execute(query)
    dataset = result_proxy.fetchall()
    return parse_data(dataset)


def day_average(labels, data):

    sorted_data = {'pm1 factory': [[data['pm1 factory'][0]]],
                   'pm2.5 factory': [[data['pm2.5 factory'][0]]],
                   'pm10 factory': [[data['pm10 factory'][0]]],
                   'pm1 atmospheric': [[data['pm1 atmospheric'][0]]],
                   'pm2.5 atmospheric': [[data['pm2.5 atmospheric'][0]]],
                   'pm10 atmospheric': [[data['pm10 atmospheric'][0]]]}

    sorted_labels = [labels[0].split(' ')[0]]

    j = 0
    for i in range(1, len(labels)):
        day = labels[i].split(' ')[0]
        if sorted_labels[j] == day:
            sorted_data['pm1 factory'][j].append(data['pm1 factory'][i])
            sorted_data['pm2.5 factory'][j].append(data['pm2.5 factory'][i])
            sorted_data['pm10 factory'][j].append(data['pm10 factory'][i])
            sorted_data['pm1 atmospheric'][j].append(data['pm1 atmospheric'][i])
            sorted_data['pm2.5 atmospheric'][j].append(data['pm2.5 atmospheric'][i])
            sorted_data['pm10 atmospheric'][j].append(data['pm10 atmospheric'][i])
        else:
            sorted_data['pm1 factory'].append([data['pm1 factory'][i]])
            sorted_data['pm2.5 factory'].append([data['pm2.5 factory'][i]])
            sorted_data['pm10 factory'].append([data['pm10 factory'][i]])
            sorted_data['pm1 atmospheric'].append([data['pm1 atmospheric'][i]])
            sorted_data['pm2.5 atmospheric'].append([data['pm2.5 atmospheric'][i]])
            sorted_data['pm10 atmospheric'].append([data['pm10 atmospheric'][i]])
            sorted_labels.append(day)
            j += 1

    for key in sorted_data:
        for day in range(len(sorted_data[key])):
            average = np.average(sorted_data[key][day])
            sorted_data[key][day] = average

    return sorted_labels, sorted_data


@app.route('/')
def main():
    labels, data = query_data(hours=24)
    return render_template('index.html', timerange="24 hours", data=data, labels=labels, type='raw', zip=zip)


@app.route('/days=<int:days>')
def last_days(days):
    labels, data = query_data(days=days)
    labels, data = day_average(labels, data)
    return render_template('index.html', timerange="%s days" % days, data=data, labels=labels, type='averaged', zip=zip)


@app.route('/hours=<int:hours>')
def last_hours(hours):
    labels, data = query_data(hours=hours)
    return render_template('index.html', timerange="%s hours" % hours, data=data, labels=labels, type='raw', zip=zip)


@app.route('/minutes=<int:minutes>')
def last_minutes(minutes):
    labels, data = query_data(minutes=minutes)
    return render_template('index.html', timerange="%s minutes" % minutes, data=data, labels=labels, type='raw', zip=zip)


if __name__ == '__main__':

    app.run(debug=Config.DEBUG, port=Config.PORT)
