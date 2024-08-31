package pl.activitymusic.backend.business.spotify.control

import org.springframework.stereotype.Service
import pl.activitymusic.backend.business.common.entity.LocalDateTimeSpan
import pl.activitymusic.backend.business.spotify.boundary.SpotifyOperations
import pl.activitymusic.backend.business.spotify.entity.AuthorizationResponse
import pl.activitymusic.backend.business.spotify.entity.Tokens
import pl.activitymusic.backend.business.spotify.entity.Track
import pl.activitymusic.backend.business.spotify.entity.UserProfileData


@Service
internal class DefaultSpotifyOperations(
    private val authorizationOperations: SpotifyAuthorizationOperations,
    private val api: SpotifyApi
) : SpotifyOperations {

    override fun getAuthorizationUrl(): String {
        return authorizationOperations.getAuthorizationUrl()
    }

    override fun getTokens(response: AuthorizationResponse): Tokens {
        return authorizationOperations.getTokens(response)
    }

    override fun getTokens(refreshToken: String): Tokens {
        return authorizationOperations.getTokens(refreshToken)
    }

    override fun getUserProfile(accessToken: String): UserProfileData {
        val authorizationHeader = "Bearer $accessToken"
        return api.getProfile(authorizationHeader)
    }

    override fun getTracks(span: LocalDateTimeSpan): List<Track> {
        TODO("Not yet implemented")
    }
}
