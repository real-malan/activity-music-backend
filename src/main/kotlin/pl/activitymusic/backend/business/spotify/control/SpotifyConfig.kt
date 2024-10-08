package pl.activitymusic.backend.business.spotify.control

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
internal class SpotifyConfig(
    private val properties: SpotifyProperties
) {

    @Bean
    fun spotifyApi(): SpotifyApi {
        val restClient = RestClient.builder()
            .baseUrl(properties.apiUrl)
            .build()

        return HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(restClient))
            .build()
            .createClient(SpotifyApi::class.java)
    }
}
