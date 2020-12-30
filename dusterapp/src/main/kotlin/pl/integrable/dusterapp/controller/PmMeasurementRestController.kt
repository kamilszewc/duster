package pl.integrable.dusterapp.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.integrable.dusterapp.model.PmMeasurement
import pl.integrable.dusterapp.repository.PmMeasurementRepository

@RestController
class PmMeasurementRestController @Autowired constructor(val pmMeasurementRepository: PmMeasurementRepository) {

    @PostMapping("/api/v1/recordPmMeasurement")
    fun recordPmMeasurement(@RequestBody pmMeasurement: PmMeasurement) {

        pmMeasurementRepository.save(pmMeasurement)
    }
}