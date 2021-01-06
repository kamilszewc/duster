package pl.integrable.dusterapp.provider

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.integrable.dusterapp.model.PressureMeasurement
import pl.integrable.dusterapp.repository.PressureMeasurementRepository
import java.time.LocalDateTime

@Service
class PressureMeasurementProvider @Autowired constructor(val pressureMeasurementRepository: PressureMeasurementRepository) {

    fun provideLastMeasurements(fromlocalDateTime: LocalDateTime, averageType: String) : List<PressureMeasurement> {

        if (averageType == "hourly") {
            val averagedPressureMeasurements : MutableList<PressureMeasurement> = mutableListOf()

            var untilMoment = LocalDateTime.now().plusHours(1).withMinute(0).withSecond(0)

            while (untilMoment.isAfter(fromlocalDateTime)) {

                val fromMoment = untilMoment.minusHours(1)
                val measurement = PressureMeasurement(0.0, untilMoment.withMinute(30))
                val measurements = pressureMeasurementRepository.findAllByDateBetween(fromMoment, untilMoment)

                measurements.forEach {
                    measurement.pressure += it.pressure
                }

                measurement.pressure /= measurements.size

                averagedPressureMeasurements.add(measurement)

                untilMoment = fromMoment
            }
            return averagedPressureMeasurements.reversed();

        }
        if (averageType == "daily") {
            val averagePressureMeasurements : MutableList<PressureMeasurement> = mutableListOf()

            var untilMoment = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0)

            while (untilMoment.isAfter(fromlocalDateTime)) {

                val fromMoment = untilMoment.minusDays(1)
                val measurement = PressureMeasurement(0.0, untilMoment.withHour(12))
                val measurements = pressureMeasurementRepository.findAllByDateBetween(fromMoment, untilMoment)

                measurements.forEach {
                    measurement.pressure += it.pressure
                }

                measurement.pressure /= measurements.size

                averagePressureMeasurements.add(measurement)

                untilMoment = fromMoment
            }
            return averagePressureMeasurements.reversed();

        } else {
            val measurements = pressureMeasurementRepository.findAllByDateBetween(fromlocalDateTime, LocalDateTime.now())
            return measurements
        }
    }
}