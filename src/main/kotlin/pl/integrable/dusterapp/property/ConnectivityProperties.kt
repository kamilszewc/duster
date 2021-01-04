package pl.integrable.dusterapp.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "connectivity")
class ConnectivityProperties {
    var serverUrl: String = ""
    var serverToken: String = ""
}