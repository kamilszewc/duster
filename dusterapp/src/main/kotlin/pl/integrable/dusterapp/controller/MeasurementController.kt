package pl.integrable.dusterapp.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import pl.integrable.dusterapp.provider.MeasurementProvider
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Controller
class MeasurementController @Autowired constructor(private val measurementProvider: MeasurementProvider) {

    @GetMapping("/measurements")
    private fun measurements(@RequestParam(required = true, name = "time-range") timeRange: String?,
                             model: Model) : String {

        var localTimeDate = LocalDateTime.now()
        var pattern = "yyyy-MM-dd hh:mm"
        var averageType = "none"

        if (timeRange == "hour") {
            localTimeDate = localTimeDate.minusHours(1)
            averageType = "none"
            pattern = "hh:mm"
        }
        else if (timeRange == "day") {
            localTimeDate = localTimeDate.minusDays(1)
            averageType = "none"
            pattern = "hh:mm"
        }
        else if (timeRange == "week") {
            localTimeDate = localTimeDate.minusWeeks(1)
            averageType = "hourly"
            pattern = "yyyy-MM-dd hh:mm"
        }
        else if (timeRange == "month") {
            localTimeDate = localTimeDate.minusMonths(1)
            averageType = "daily"
            pattern = "yyyy-MM-dd"
        }
        else if (timeRange == "year") {
            localTimeDate = localTimeDate.minusYears(1)
            averageType = "daily"
            pattern = "yyyy-MM-dd"
        }

        val measurements = measurementProvider.provideLastMeasurements(localTimeDate, averageType)

        val plotDate: MutableList<String> = mutableListOf()
        val plotPm10Atmospheric: MutableList<Double> = mutableListOf()
        val plotPm25Atmospheric: MutableList<Double> = mutableListOf()
        val plotPm100Atmospheric: MutableList<Double> = mutableListOf()

        measurements.forEach { measurement ->
            plotDate.add(measurement.date.toLocalDateTime().format(DateTimeFormatter.ofPattern(pattern)))
            plotPm10Atmospheric.add(measurement.pm10Atmospheric)
            plotPm25Atmospheric.add(measurement.pm25Atmospheric)
            plotPm100Atmospheric.add(measurement.pm100Atmospheric)
        }

        model.addAttribute("timeRange", timeRange)

        model.addAttribute("plotDate", plotDate)
        model.addAttribute("plotPm10Atmospheric", plotPm10Atmospheric)
        model.addAttribute("plotPm25Atmospheric", plotPm25Atmospheric)
        model.addAttribute("plotPm100Atmospheric", plotPm100Atmospheric)

        return "measurements"
    }
}