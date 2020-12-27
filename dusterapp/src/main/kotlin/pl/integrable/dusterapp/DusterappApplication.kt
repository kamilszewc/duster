package pl.integrable.dusterapp

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import pl.integrable.dusterapp.model.Measurement
import pl.integrable.dusterapp.provider.MeasurementProvider
import java.sql.ResultSet
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.TemporalUnit

@SpringBootApplication
class DusterappApplication {

    @Bean
    fun init(measurementProvider: MeasurementProvider) = CommandLineRunner {

        val results = measurementProvider.provideLastMeasurements(LocalDateTime.now().minusMinutes(75))
        results.forEach { result -> println(result.date) }

    }
}

fun main(args: Array<String>) {
    runApplication<DusterappApplication>(*args)
}
