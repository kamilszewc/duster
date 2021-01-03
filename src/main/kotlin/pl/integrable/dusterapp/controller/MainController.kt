package pl.integrable.dusterapp.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MainController {

    @GetMapping("/")
    fun main() : String {

        return "redirect:/measurements/pm?time-range=day"
    }
}