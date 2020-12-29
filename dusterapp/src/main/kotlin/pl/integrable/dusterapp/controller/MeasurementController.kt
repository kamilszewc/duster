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
    private fun measurements(@RequestParam(required = false) hours: Long?,
                             @RequestParam(required = false) minutes: Long?,
                             @RequestParam(required = false) seconds: Long?,
                             @RequestParam(required = false, name = "time-range") timeRange: String?,
                             model: Model) : String {

        var localTimeDate = LocalDateTime.now()
        if (hours != null) localTimeDate = localTimeDate.minusHours(hours)
        if (minutes != null) localTimeDate = localTimeDate.minusMinutes(minutes)
        if (seconds != null) localTimeDate = localTimeDate.minusSeconds(seconds)

        if (timeRange == "hour") localTimeDate = localTimeDate.minusHours(1)
        if (timeRange == "day") localTimeDate = localTimeDate.minusDays(1)
        if (timeRange == "week") localTimeDate = localTimeDate.minusWeeks(1)
        if (timeRange == "month") localTimeDate = localTimeDate.minusMonths(1)
        if (timeRange == "year") localTimeDate = localTimeDate.minusYears(1)

        val measurements = measurementProvider.provideLastMeasurements(localTimeDate, timeRange)

        val plotDate: MutableList<String> = mutableListOf()
        val plotPm10Atmospheric: MutableList<Double> = mutableListOf()
        val plotPm25Atmospheric: MutableList<Double> = mutableListOf()
        val plotPm100Atmospheric: MutableList<Double> = mutableListOf()

        var pattern = "yyyy-MM-dd hh:mm"
        if (timeRange == "hour" || timeRange == "day") {
            pattern = "hh:mm"
        }

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