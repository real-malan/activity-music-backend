package pl.activitymusic.backend.business.strava.boundary

import pl.activitymusic.backend.business.oauth.entity.TokensResponse
import pl.activitymusic.backend.business.strava.entity.StravaAuthorizationResponse

interface StravaOperations {
    fun getAuthorizationUrl(): String

    fun getTokens(response: StravaAuthorizationResponse): TokensResponse
}
