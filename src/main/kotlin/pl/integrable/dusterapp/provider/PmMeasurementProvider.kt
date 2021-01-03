package pl.integrable.dusterapp.provider

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.integrable.dusterapp.model.PmMeasurement
import pl.integrable.dusterapp.repository.PmMeasurementRepository
import java.time.LocalDateTime

@Service
class PmMeasurementProvider @Autowired constructor(val pmMeasurementRepository: PmMeasurementRepository) {

    fun provideLastMeasurements(fromlocalDateTime: LocalDateTime, averageType: String) : List<PmMeasurement> {

        if (averageType == "hourly") {
            val averagedPmMeasurements : MutableList<PmMeasurement> = mutableListOf()

            var untilMoment = LocalDateTime.now().plusHours(1).withMinute(0).withSecond(0)

            while (untilMoment.isAfter(fromlocalDateTime)) {

                val fromMoment = untilMoment.minusHours(1)
                val measurement = PmMeasurement(0.0, 0.0, 0.0, untilMoment.withMinute(30))
                val measurements = pmMeasurementRepository.findAllByDateBetween(fromMoment, untilMoment)

                measurements.forEach {
                    measurement.pm10 += it.pm10
                    measurement.pm25 += it.pm25
                    measurement.pm100 += it.pm100
                }

                measurement.pm10 /= measurements.size
                measurement.pm25 /= measurements.size
                measurement.pm100 /= measurements.size

                averagedPmMeasurements.add(measurement)

                untilMoment = fromMoment
            }
            return averagedPmMeasurements.reversed();

        }
        if (averageType == "daily") {
            val averagedPmMeasurements : MutableList<PmMeasurement> = mutableListOf()

            var untilMoment = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0)

            while (untilMoment.isAfter(fromlocalDateTime)) {

                val fromMoment = untilMoment.minusDays(1)
                val measurement = PmMeasurement(0.0, 0.0, 0.0, untilMoment.withHour(12))
                val measurements = pmMeasurementRepository.findAllByDateBetween(fromMoment, untilMoment)

                measurements.forEach {
                    measurement.pm10 += it.pm10
                    measurement.pm25 += it.pm25
                    measurement.pm100 += it.pm100
                }

                measurement.pm10 /= measurements.size
                measurement.pm25 /= measurements.size
                measurement.pm100 /= measurements.size

                averagedPmMeasurements.add(measurement)

                untilMoment = fromMoment
            }
            return averagedPmMeasurements.reversed();

        } else {
            val measurements = pmMeasurementRepository.findAllByDateBetween(fromlocalDateTime, LocalDateTime.now())
            return measurements
        }
    }
}