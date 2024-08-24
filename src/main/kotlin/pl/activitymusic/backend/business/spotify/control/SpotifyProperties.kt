package pl.activitymusic.backend.business.spotify.control

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "spotify")
internal data class SpotifyProperties @ConstructorBinding constructor(
    val accountsUrl: String,
    val apiUrl: String,
    val client: Client
) {
    data class Client (
        val id: String,
        val secret: String
    )
}
