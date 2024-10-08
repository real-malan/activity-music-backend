package pl.activitymusic.backend.business.spotify.control

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import pl.activitymusic.backend.business.oauth.entity.OauthProperties

@ConfigurationProperties(prefix = "spotify")
internal data class SpotifyProperties @ConstructorBinding constructor(
    val apiUrl: String,
    val oauth: OauthProperties
)
