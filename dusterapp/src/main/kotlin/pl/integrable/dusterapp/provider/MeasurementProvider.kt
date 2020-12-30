package pl.integrable.dusterapp.provider

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service
import pl.integrable.dusterapp.model.Measurement
import java.sql.ResultSet
import java.sql.Timestamp
import java.time.LocalDateTime

@Service
class MeasurementProvider @Autowired constructor(private val jdbcTemplate: JdbcTemplate) {

    private fun getMeasurementsFromDatabase(fromLocalDateTime: LocalDateTime, untilLocalDateTime: LocalDateTime) : List<Measurement> {
        var rowMapper: RowMapper<Measurement> = RowMapper<Measurement> { rs: ResultSet, _: Int ->
            Measurement(
                rs.getTimestamp("date"),
                rs.getDouble("pm1 atmospheric"),
                rs.getDouble("pm2.5 atmospheric"),
                rs.getDouble("pm10 atmospheric"),
            )
        }

        val fromTimestamp = Timestamp.valueOf(fromLocalDateTime)
        val untilTimestamp = Timestamp.valueOf(untilLocalDateTime)
        val query = "SELECT * FROM concentration WHERE date >= '$fromTimestamp' and date <= '$untilTimestamp'"
        var results = jdbcTemplate.query(query, rowMapper)

        return results
    }

    fun provideLastMeasurements(fromlocalDateTime: LocalDateTime, averageType: String) : List<Measurement> {

        if (averageType == "hourly") {
            val averagedMeasurements : MutableList<Measurement> = mutableListOf()

            var untilMoment = LocalDateTime.now().plusHours(1).withMinute(0).withSecond(0)

            while (untilMoment.isAfter(fromlocalDateTime)) {

                val fromMoment = untilMoment.minusHours(1)
                val measurement = Measurement(Timestamp.valueOf(untilMoment.withMinute(30)), 0.0, 0.0, 0.0)
                val measurements = getMeasurementsFromDatabase(fromMoment, untilMoment)

                measurements.forEach {
                    measurement.pm10Atmospheric += it.pm10Atmospheric
                    measurement.pm25Atmospheric += it.pm25Atmospheric
                    measurement.pm100Atmospheric += it.pm100Atmospheric
                }

                measurement.pm10Atmospheric /= measurements.size
                measurement.pm25Atmospheric /= measurements.size
                measurement.pm100Atmospheric /= measurements.size

                averagedMeasurements.add(measurement)

                untilMoment = fromMoment
            }
            return averagedMeasurements;

        }
        if (averageType == "daily") {
            val averagedMeasurements : MutableList<Measurement> = mutableListOf()

            var untilMoment = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0)

            while (untilMoment.isAfter(fromlocalDateTime)) {

                val fromMoment = untilMoment.minusDays(1)
                val measurement = Measurement(Timestamp.valueOf(untilMoment.withHour(12)), 0.0, 0.0, 0.0)
                val measurements = getMeasurementsFromDatabase(fromMoment, untilMoment)

                measurements.forEach {
                    measurement.pm10Atmospheric += it.pm10Atmospheric
                    measurement.pm25Atmospheric += it.pm25Atmospheric
                    measurement.pm100Atmospheric += it.pm100Atmospheric
                }

                measurement.pm10Atmospheric /= measurements.size
                measurement.pm25Atmospheric /= measurements.size
                measurement.pm100Atmospheric /= measurements.size

                averagedMeasurements.add(measurement)

                untilMoment = fromMoment
            }
            return averagedMeasurements;

        } else {
            val measurements = getMeasurementsFromDatabase(fromlocalDateTime, LocalDateTime.now())
            return measurements
        }

    }
}