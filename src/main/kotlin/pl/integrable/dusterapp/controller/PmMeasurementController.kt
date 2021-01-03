package pl.integrable.dusterapp.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import pl.integrable.dusterapp.provider.PmMeasurementProvider
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Controller
class PmMeasurementController @Autowired constructor(private val pmMeasurementProvider: PmMeasurementProvider) {

    @GetMapping("/measurements/pm")
    private fun measurements(@RequestParam(required = true, name = "time-range") timeRange: String?,
                             model: Model) : String {

        var localTimeDate = LocalDateTime.now()
        var pattern = "yyyy-MM-dd HH:mm"
        var averageType = "none"

        if (timeRange == "hour") {
            localTimeDate = localTimeDate.minusHours(1)
            averageType = "none"
            pattern = "HH:mm"
        }
        else if (timeRange == "day") {
            localTimeDate = localTimeDate.minusDays(1)
            averageType = "none"
            pattern = "HH:mm"
        }
        else if (timeRange == "week") {
            localTimeDate = localTimeDate.minusWeeks(1)
            averageType = "hourly"
            pattern = "yyyy-MM-dd HH:mm"
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

        val measurements = pmMeasurementProvider.provideLastMeasurements(localTimeDate, averageType)

        val plotDate: MutableList<String> = mutableListOf()
        val plotPm10Atmospheric: MutableList<Double> = mutableListOf()
        val plotPm25Atmospheric: MutableList<Double> = mutableListOf()
        val plotPm100Atmospheric: MutableList<Double> = mutableListOf()

        measurements.forEach { measurement ->
            measurement.date?.let { plotDate.add(it.format(DateTimeFormatter.ofPattern(pattern))) }
            plotPm10Atmospheric.add(measurement.pm10)
            plotPm25Atmospheric.add(measurement.pm25)
            plotPm100Atmospheric.add(measurement.pm100)
        }

        model.addAttribute("timeRange", timeRange)

        model.addAttribute("plotDate", plotDate)
        model.addAttribute("plotPm10Atmospheric", plotPm10Atmospheric)
        model.addAttribute("plotPm25Atmospheric", plotPm25Atmospheric)
        model.addAttribute("plotPm100Atmospheric", plotPm100Atmospheric)

        return "measurements/pm"
    }
}