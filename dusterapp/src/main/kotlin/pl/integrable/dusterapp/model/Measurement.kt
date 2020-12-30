package pl.integrable.dusterapp.model

import java.sql.Timestamp
import java.time.LocalDateTime

class Measurement(
    var date: Timestamp,
    var pm10Atmospheric: Double,
    var pm25Atmospheric: Double,
    var pm100Atmospheric: Double
)