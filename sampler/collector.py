
class Collector(object):

    def __init__(self, config, broker):
        self.config = config
        self.broker = broker

    def collect(self):

        try:
            data_to_send = self.broker.model.get_data_to_send()
            print("Collected: " + str(data_to_send))

            try:
                print("Trying to send...")
                self.broker.send(data_to_send)
            except Exception as ex:
                print("Can not send :(")
            else:
                print("Message successfully sent")

        except Exception as ex:
            print(ex.message)