package pl.activitymusic.backend.business.strava.control

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import pl.activitymusic.backend.business.oauth.entity.OauthProperties

@ConfigurationProperties(prefix = "strava")
internal data class StravaProperties @ConstructorBinding constructor(
    val apiUrl: String,
    val oauth: OauthProperties
)
