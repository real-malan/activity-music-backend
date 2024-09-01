package pl.activitymusic.backend.business.spotify.boundary

import pl.activitymusic.backend.business.common.entity.LocalDateTimeSpan
import pl.activitymusic.backend.business.oauth.entity.AuthorizationResponse
import pl.activitymusic.backend.business.oauth.entity.TokensResponse
import pl.activitymusic.backend.business.spotify.entity.Track
import pl.activitymusic.backend.business.spotify.entity.UserProfileData

interface SpotifyOperations {
    fun getAuthorizationUrl(): String

    fun getTokens(response: AuthorizationResponse): TokensResponse

    fun getUserProfile(accessToken: String): UserProfileData

    fun getTracks(span: LocalDateTimeSpan): List<Track>
}
