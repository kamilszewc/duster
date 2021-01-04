package pl.integrable.dusterapp.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.integrable.dusterapp.model.PmMeasurement
import pl.integrable.dusterapp.model.TemperatureMeasurement
import pl.integrable.dusterapp.property.ConnectivityProperties
import pl.integrable.dusterapp.provider.ApiConsumer
import pl.integrable.dusterapp.repository.TemperatureMeasurementRepository

@RestController
class TemperatureMeasurementRestController @Autowired constructor(
    val temperatureMeasurementRepository: TemperatureMeasurementRepository,
    val connectivityProperties: ConnectivityProperties,
    val apiConsumer: ApiConsumer
) {

    @PostMapping("/api/v1/recordTemperatureMeasurement")
    fun recordPmMeasurement(@RequestBody temperatureMeasurement: TemperatureMeasurement) {

        temperatureMeasurementRepository.save(temperatureMeasurement)

        if (connectivityProperties.serverUrl != "") {
            val url = connectivityProperties.serverUrl + "/temperature"
            val token = connectivityProperties.serverToken
            try {
                val response = apiConsumer.consumePost<String, TemperatureMeasurement>(
                    url,
                    temperatureMeasurement,
                    token,
                    object : ParameterizedTypeReference<String>() {})
            } catch (e: Exception) {
                println("Can not send data to server...")
            }
        }
    }
}