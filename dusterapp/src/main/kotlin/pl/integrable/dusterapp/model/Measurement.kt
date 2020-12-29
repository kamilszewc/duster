package pl.integrable.dusterapp.model

import java.sql.Date
import java.sql.Timestamp

class Measurement(
    val date: Timestamp,
    val pm10Atmospheric: Double,
    val pm25Atmospheric: Double,
    val pm100Atmospheric: Double
)