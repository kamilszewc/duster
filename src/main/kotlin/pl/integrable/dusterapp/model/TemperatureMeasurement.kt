package pl.integrable.dusterapp.model

import com.fasterxml.jackson.annotation.JsonFormat
import org.hibernate.annotations.CreationTimestamp
import org.springframework.format.annotation.DateTimeFormat
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
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
    var date: LocalDateTime? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
) {
}