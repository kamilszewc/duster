package pl.integrable.dusterapp

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import pl.integrable.dusterapp.provider.MeasurementProvider
import java.time.LocalDateTime

@SpringBootApplication
class DusterappApplication {
}

fun main(args: Array<String>) {
    runApplication<DusterappApplication>(*args)
}
