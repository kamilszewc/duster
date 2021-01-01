package pl.integrable.dusterapp.model

import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

enum class TemperatureUnit {
    CELSIUS, FAHRENHEIT, KELVIN
}

@Entity(name="db_temperature")
class TemperatureMeasurement(
    var temperature: Double,
    var unit: TemperatureUnit,
    @CreationTimestamp
    var date: LocalDateTime? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
) {
}