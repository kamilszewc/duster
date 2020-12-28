package pl.integrable.dusterapp.model

import java.sql.Date
import java.sql.Timestamp

class Measurement(
    val date: Timestamp,
    val pm10Factory: Double,
    val pm25Factory: Double,
    val pm100Factory: Double,
    val pm10Atmospheric: Double,
    val pm25Atmospheric: Double,
    val pm100Atmospheric: Double
)