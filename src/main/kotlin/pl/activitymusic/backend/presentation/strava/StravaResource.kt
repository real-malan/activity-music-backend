package pl.activitymusic.backend.presentation.strava

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.activitymusic.backend.business.oauth.entity.TokensResponse
import pl.activitymusic.backend.business.strava.boundary.StravaOperations
import pl.activitymusic.backend.business.strava.entity.StravaAuthorizationResponse
import pl.activitymusic.backend.presentation.Endpoints

@RestController
class StravaResource(
    private val stravaOperations: StravaOperations
) {

    companion object {
        const val STRAVA_EXTERNAL_API = "${Endpoints.EXTERNAL_API_URI}/strava"
        private const val STRAVA_API = "${Endpoints.API_URI}/strava"
    }

    @GetMapping("$STRAVA_API/authorize")
    fun getAuthorizationUrl(): String {
        return stravaOperations.getAuthorizationUrl()
    }

    @GetMapping("$STRAVA_EXTERNAL_API/authorization-callback")
    fun authorizationCallback(response: StravaAuthorizationResponse) {
        val tokens: TokensResponse = stravaOperations.getTokens(response)
        println(tokens)
    }
}
