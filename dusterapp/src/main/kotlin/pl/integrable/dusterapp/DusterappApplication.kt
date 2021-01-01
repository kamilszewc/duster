package pl.integrable.dusterapp

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import pl.integrable.dusterapp.property.ModulesProperties

@SpringBootApplication
@EnableConfigurationProperties(ModulesProperties::class)
class DusterappApplication {

    @Bean
    public fun init(modulesProperties: ModulesProperties) = CommandLineRunner {
        println("PM module: " + modulesProperties.pm)
        println("Temperature module: " + modulesProperties.temperature)
    }
}


fun main(args: Array<String>) {
    runApplication<DusterappApplication>(*args)
}
