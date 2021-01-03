package pl.integrable.dusterapp.repository

import org.springframework.data.jpa.repository.JpaRepository
import pl.integrable.dusterapp.model.TemperatureMeasurement
import java.time.LocalDateTime

interface TemperatureMeasurementRepository : JpaRepository<TemperatureMeasurement, Long> {

    fun findAllByDateBetween(fromLocalDateTime: LocalDateTime, untilLocalDateTime: LocalDateTime) : List<TemperatureMeasurement>;
}