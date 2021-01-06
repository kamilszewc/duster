package pl.integrable.dusterapp.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import pl.integrable.dusterapp.provider.PressureMeasurementProvider
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Controller
class PressureMeasurementController @Autowired constructor(private val pressureMeasurementProvider: PressureMeasurementProvider) {

    @GetMapping("/measurements/pressure")
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

        val measurements = pressureMeasurementProvider.provideLastMeasurements(localTimeDate, averageType)

        val plotDate: MutableList<String> = mutableListOf()
        val plotPressure: MutableList<Double> = mutableListOf()

        measurements.forEach { measurement ->
            measurement.date?.let { plotDate.add(it.format(DateTimeFormatter.ofPattern(pattern))) }
            plotPressure.add(measurement.pressure)
        }

        model.addAttribute("timeRange", timeRange)

        model.addAttribute("plotDate", plotDate)
        model.addAttribute("plotPressure", plotPressure)

        return "measurements/pressure"
    }
}