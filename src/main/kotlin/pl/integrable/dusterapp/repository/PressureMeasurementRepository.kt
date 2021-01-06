package pl.integrable.dusterapp.repository

import org.springframework.data.jpa.repository.JpaRepository
import pl.integrable.dusterapp.model.PressureMeasurement
import java.time.LocalDateTime

interface PressureMeasurementRepository : JpaRepository<PressureMeasurement, Long> {

    fun findAllByDateBetween(fromLocalDateTime: LocalDateTime, untilLocalDateTime: LocalDateTime) : List<PressureMeasurement>;
}