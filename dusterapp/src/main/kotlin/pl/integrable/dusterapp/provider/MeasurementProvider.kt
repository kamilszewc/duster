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

    fun provideLastMeasurements(localDateTime: LocalDateTime) : List<Measurement> {

        var rowMapper: RowMapper<Measurement> = RowMapper<Measurement> { rs: ResultSet, rowNum: Int ->
            Measurement(rs.getTimestamp("date"))
        }

        val timestamp = Timestamp.valueOf(localDateTime)
        val query = "SELECT * FROM concentration WHERE date > '$timestamp'"
        var results = jdbcTemplate.query(query, rowMapper)

        results.forEach { result -> println(result.date) }

        return results
    }
}