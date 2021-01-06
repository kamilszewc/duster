package pl.integrable.dusterapp.repository

import org.springframework.data.jpa.repository.JpaRepository
import pl.integrable.dusterapp.model.HumidityMeasurement
import java.time.LocalDateTime

interface HumidityMeasurementRepository : JpaRepository<HumidityMeasurement, Long> {

    fun findAllByDateBetween(fromLocalDateTime: LocalDateTime, untilLocalDateTime: LocalDateTime) : List<HumidityMeasurement>;
}