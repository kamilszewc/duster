package pl.integrable.dusterapp.provider

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class ApiConsumer {

//    private val clientHttpRequestFactory: ClientHttpRequestFactory
//        private get() {
//            val timeout = 4000
//            val clientHttpRequestFactory = HttpComponentsClientHttpRequestFactory()
//            clientHttpRequestFactory.setConnectTimeout(timeout)
//            clientHttpRequestFactory.setReadTimeout(timeout)
//            clientHttpRequestFactory.setConnectionRequestTimeout(timeout)
//            return clientHttpRequestFactory
//        }

    fun <T> consumeGet(url: String, token: String, typeRef: ParameterizedTypeReference<*>?): T? {

        // Get task command template
        val httpHeaders = HttpHeaders()
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json")
        val authHeader: String = "Bearer $token"
        httpHeaders.add("Authorization", authHeader)
        val httpEntity: HttpEntity<*> = HttpEntity<Any?>(httpHeaders)
        val restTemplate = RestTemplate()
        val response: ResponseEntity<T> = restTemplate.exchange(
            url,
            HttpMethod.GET,
            httpEntity,
            typeRef as ParameterizedTypeReference<Any>
        ) as ResponseEntity<T>
        return response.getBody()
    }

    fun <T, U> consumePost(url: String, requestBody: U?, token: String, typeRef: ParameterizedTypeReference<*>?): T? {

        // Get task command template
        val httpHeaders = HttpHeaders()
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json")
        val authHeader: String = "Bearer $token"
        httpHeaders.add("Authorization", authHeader)
        val httpEntity: HttpEntity<*> = HttpEntity<Any?>(requestBody, httpHeaders)
        val restTemplate = RestTemplate()
        val response: ResponseEntity<T> = restTemplate.exchange(
            url,
            HttpMethod.GET,
            httpEntity,
            typeRef as ParameterizedTypeReference<Any>
        ) as ResponseEntity<T>
        return response.getBody()
    }
}