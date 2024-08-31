package pl.activitymusic.backend.business.spotify.boundary

import pl.activitymusic.backend.business.common.entity.LocalDateTimeSpan
import pl.activitymusic.backend.business.spotify.entity.AuthorizationResponse
import pl.activitymusic.backend.business.spotify.entity.Tokens
import pl.activitymusic.backend.business.spotify.entity.Track
import pl.activitymusic.backend.business.spotify.entity.UserProfileData

interface SpotifyOperations {

    fun getAuthorizationUrl(): String

    fun getTokens(response: AuthorizationResponse): Tokens

    fun getTokens(refreshToken: String): Tokens

    fun getUserProfile(accessToken: String): UserProfileData

    fun getTracks(span: LocalDateTimeSpan): List<Track>
}
