package pl.integrable.dusterapp.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.integrable.dusterapp.model.PmMeasurement
import pl.integrable.dusterapp.property.ConnectivityProperties
import pl.integrable.dusterapp.provider.ApiConsumer
import pl.integrable.dusterapp.repository.PmMeasurementRepository

@RestController
class PmMeasurementRestController @Autowired constructor(
    val pmMeasurementRepository: PmMeasurementRepository,
    val connectivityProperties: ConnectivityProperties,
    val apiConsumer: ApiConsumer
) {

    @PostMapping("/api/v1/recordPmMeasurement")
    fun recordPmMeasurement(@RequestBody pmMeasurement: PmMeasurement) {

        pmMeasurementRepository.save(pmMeasurement)

        if (connectivityProperties.serverUrl != "") {
            val url = connectivityProperties.serverUrl + "/pm"
            val token = connectivityProperties.serverToken
            try {
                val response = apiConsumer.consumePost<String, PmMeasurement>(
                    url,
                    pmMeasurement,
                    token,
                    object : ParameterizedTypeReference<String>() {})
            } catch (e: Exception) {
                println("Can not send data to server...")
            }
        }
    }
}