package pl.integrable.dusterapp.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.integrable.dusterapp.model.PressureMeasurement
import pl.integrable.dusterapp.payload.Status
import pl.integrable.dusterapp.property.ConnectivityProperties
import pl.integrable.dusterapp.provider.ApiConsumer
import pl.integrable.dusterapp.repository.PressureMeasurementRepository

@RestController
class PressureMeasurementRestController @Autowired constructor(
    val pressureMeasurementRepository: PressureMeasurementRepository,
    val connectivityProperties: ConnectivityProperties,
    val apiConsumer: ApiConsumer
) {

    @PostMapping("/api/v1/record/pressure")
    fun recordPmMeasurement(@RequestBody pressureMeasurement: PressureMeasurement) {

        pressureMeasurementRepository.save(pressureMeasurement)

        if (connectivityProperties.serverUrl != "") {
            val url = connectivityProperties.serverUrl + "/pressure"
            val token = connectivityProperties.serverToken
            try {
                val response = apiConsumer.consumePost<Status<String>, PressureMeasurement>(
                    url,
                    pressureMeasurement,
                    token,
                    object : ParameterizedTypeReference<Status<String>>() {})

                println("Response: " + let { response?.message })
            } catch (e: Exception) {
                println("Can not send data to server...")
            }
        }
    }
}