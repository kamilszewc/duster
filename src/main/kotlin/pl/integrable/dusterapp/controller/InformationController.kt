package pl.integrable.dusterapp.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class InformationController {

    @GetMapping("/information")
    fun information() : String {

        return "information"
    }
}