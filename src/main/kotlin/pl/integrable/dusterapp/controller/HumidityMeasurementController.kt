package pl.integrable.dusterapp.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import pl.integrable.dusterapp.provider.HumidityMeasurementProvider
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Controller
class HumidityMeasurementController @Autowired constructor(private val humidityMeasurementProvider: HumidityMeasurementProvider) {

    @GetMapping("/measurements/humidity")
    private fun measurements(@RequestParam(required = true, name = "time-range") timeRange: String?, model: Model): String {

        var localTimeDate = LocalDateTime.now()
        var pattern = "yyyy-MM-dd HH:mm"
        var averageType = "none"

        if (timeRange == "hour") {
            localTimeDate = localTimeDate.minusHours(1)
            averageType = "none"
            pattern = "HH:mm"
        } else if (timeRange == "day") {
            localTimeDate = localTimeDate.minusDays(1)
            averageType = "none"
            pattern = "HH:mm"
        } else if (timeRange == "week") {
            localTimeDate = localTimeDate.minusWeeks(1)
            averageType = "hourly"
            pattern = "yyyy-MM-dd HH:mm"
        } else if (timeRange == "month") {
            localTimeDate = localTimeDate.minusMonths(1)
            averageType = "daily"
            pattern = "yyyy-MM-dd"
        } else if (timeRange == "year") {
            localTimeDate = localTimeDate.minusYears(1)
            averageType = "daily"
            pattern = "yyyy-MM-dd"
        }

        val measurements = humidityMeasurementProvider.provideLastMeasurements(localTimeDate, averageType)

        val plotDate: MutableList<String> = mutableListOf()
        val plotHumidity: MutableList<Double> = mutableListOf()

        measurements.forEach { measurement ->
            measurement.date?.let { plotDate.add(it.format(DateTimeFormatter.ofPattern(pattern))) }
            plotHumidity.add(measurement.humidity)
        }

        model.addAttribute("timeRange", timeRange)

        model.addAttribute("plotDate", plotDate)
        model.addAttribute("plotHumidity", plotHumidity)

        return "measurements/humidity"
    }
}