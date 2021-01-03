import time


class Collector(object):

    def __init__(self, config, broker):
        self.config = config
        self.broker = broker

    def collect(self):

        try:
            data = self.broker.model.get_data()
            print("Collected: " + str(data))

            try:
                print("Trying to send...")
                self.broker.send(data)
            except Exception as ex:
                print("Can not send :(")
            else:
                print("Message successfully sent")

        except Exception as ex:
            print(ex.message)