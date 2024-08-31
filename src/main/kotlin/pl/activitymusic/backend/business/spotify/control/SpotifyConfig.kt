package pl.activitymusic.backend.business.spotify.control

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import java.util.*

@Configuration
internal class SpotifyConfig(
    private val properties: SpotifyProperties,
    private val objectMapper: ObjectMapper
) {

    private val restClientBuilder = RestClient.builder()
        .messageConverters { converters ->
            converters.removeIf { it is MappingJackson2HttpMessageConverter }

            val snakeCaseObjectMapper = objectMapper.copy()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)

            converters.add(MappingJackson2HttpMessageConverter(snakeCaseObjectMapper))
        }

    @Bean
    fun spotifyAccountsApi(): SpotifyAccountsApi {
        val restClient = restClientBuilder
            .baseUrl("${properties.accountsUrl}/api")
            .defaultHeader(HttpHeaders.AUTHORIZATION, getAuthorizationHeaderValue())
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .build()

        return HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(restClient))
            .build()
            .createClient(SpotifyAccountsApi::class.java)
    }

    @Bean
    fun spotifyApi(): SpotifyApi {
        val restClient = restClientBuilder
            .baseUrl(properties.apiUrl)
            .build()

        return HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(restClient))
            .build()
            .createClient(SpotifyApi::class.java)
    }

    private fun getAuthorizationHeaderValue(): String {
        val client = "${properties.client.id}:${properties.client.secret}"
        val clientBase64Encoded = Base64.getEncoder().encodeToString(client.toByteArray())

        return "Basic $clientBase64Encoded"
    }
}
