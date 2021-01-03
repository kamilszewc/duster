package pl.integrable.dusterapp.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.integrable.dusterapp.model.TemperatureMeasurement
import pl.integrable.dusterapp.repository.TemperatureMeasurementRepository

@RestController
class TemperatureMeasurementRestController @Autowired constructor(val temperatureMeasurementRepository: TemperatureMeasurementRepository) {

    @PostMapping("/api/v1/recordTemperatureMeasurement")
    fun recordPmMeasurement(@RequestBody temperatureMeasurement: TemperatureMeasurement) {

        temperatureMeasurementRepository.save(temperatureMeasurement)
    }
}