package pl.integrable.dusterapp.provider

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.integrable.dusterapp.model.TemperatureMeasurement
import pl.integrable.dusterapp.model.TemperatureUnit
import pl.integrable.dusterapp.repository.TemperatureMeasurementRepository
import java.time.LocalDateTime

@Service
class TemperatureMeasurementProvider @Autowired constructor(val temperatureMeasurementRepository: TemperatureMeasurementRepository) {

    fun provideLastMeasurements(fromlocalDateTime: LocalDateTime, averageType: String) : List<TemperatureMeasurement> {

        if (averageType == "hourly") {
            val averagedTemperatureMeasurements : MutableList<TemperatureMeasurement> = mutableListOf()

            var untilMoment = LocalDateTime.now().plusHours(1).withMinute(0).withSecond(0)

            while (untilMoment.isAfter(fromlocalDateTime)) {

                val fromMoment = untilMoment.minusHours(1)
                val measurement = TemperatureMeasurement(0.0, TemperatureUnit.CELSIUS, untilMoment.withMinute(30))
                val measurements = temperatureMeasurementRepository.findAllByDateBetween(fromMoment, untilMoment)

                measurements.forEach {
                    measurement.temperature += it.temperature
                }

                measurement.temperature /= measurements.size
                if (measurements.isNotEmpty()) measurement.unit = measurements[0].unit

                averagedTemperatureMeasurements.add(measurement)

                untilMoment = fromMoment
            }
            return averagedTemperatureMeasurements.reversed();

        }
        if (averageType == "daily") {
            val averageTemperatureMeasurements : MutableList<TemperatureMeasurement> = mutableListOf()

            var untilMoment = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0)

            while (untilMoment.isAfter(fromlocalDateTime)) {

                val fromMoment = untilMoment.minusDays(1)
                val measurement = TemperatureMeasurement(0.0, TemperatureUnit.CELSIUS, untilMoment.withHour(12))
                val measurements = temperatureMeasurementRepository.findAllByDateBetween(fromMoment, untilMoment)

                measurements.forEach {
                    measurement.temperature += it.temperature
                }

                measurement.temperature /= measurements.size

                averageTemperatureMeasurements.add(measurement)
                if (measurements.isNotEmpty()) measurement.unit = measurements[0].unit

                untilMoment = fromMoment
            }
            return averageTemperatureMeasurements.reversed();

        } else {
            val measurements = temperatureMeasurementRepository.findAllByDateBetween(fromlocalDateTime, LocalDateTime.now())
            return measurements
        }
    }
}