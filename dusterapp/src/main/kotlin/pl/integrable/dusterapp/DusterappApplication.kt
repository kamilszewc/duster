package pl.integrable.dusterapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DusterappApplication {
}

fun main(args: Array<String>) {
    runApplication<DusterappApplication>(*args)
}
