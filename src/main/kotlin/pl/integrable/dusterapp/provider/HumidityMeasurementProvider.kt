package pl.integrable.dusterapp.provider

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.integrable.dusterapp.model.HumidityMeasurement
import pl.integrable.dusterapp.repository.HumidityMeasurementRepository
import java.time.LocalDateTime

@Service
class HumidityMeasurementProvider @Autowired constructor(val humidityMeasurementRepository: HumidityMeasurementRepository) {

    fun provideLastMeasurements(fromlocalDateTime: LocalDateTime, averageType: String) : List<HumidityMeasurement> {

        if (averageType == "hourly") {
            val averagedHumidityMeasurements : MutableList<HumidityMeasurement> = mutableListOf()

            var untilMoment = LocalDateTime.now().plusHours(1).withMinute(0).withSecond(0)

            while (untilMoment.isAfter(fromlocalDateTime)) {

                val fromMoment = untilMoment.minusHours(1)
                val measurement = HumidityMeasurement(0.0, untilMoment.withMinute(30))
                val measurements = humidityMeasurementRepository.findAllByDateBetween(fromMoment, untilMoment)

                measurements.forEach {
                    measurement.humidity += it.humidity
                }

                measurement.humidity /= measurements.size

                averagedHumidityMeasurements.add(measurement)

                untilMoment = fromMoment
            }
            return averagedHumidityMeasurements.reversed();

        }
        if (averageType == "daily") {
            val averageHumidityMeasurements : MutableList<HumidityMeasurement> = mutableListOf()

            var untilMoment = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0)

            while (untilMoment.isAfter(fromlocalDateTime)) {

                val fromMoment = untilMoment.minusDays(1)
                val measurement = HumidityMeasurement(0.0, untilMoment.withHour(12))
                val measurements = humidityMeasurementRepository.findAllByDateBetween(fromMoment, untilMoment)

                measurements.forEach {
                    measurement.humidity += it.humidity
                }

                measurement.humidity /= measurements.size

                averageHumidityMeasurements.add(measurement)

                untilMoment = fromMoment
            }
            return averageHumidityMeasurements.reversed();

        } else {
            val measurements = humidityMeasurementRepository.findAllByDateBetween(fromlocalDateTime, LocalDateTime.now())
            return measurements
        }
    }
}