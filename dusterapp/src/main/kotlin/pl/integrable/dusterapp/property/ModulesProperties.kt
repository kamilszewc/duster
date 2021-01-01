package pl.integrable.dusterapp.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix="modules")
class ModulesProperties {
    var pm: Boolean? = false
    var temperature: Boolean? = false
}