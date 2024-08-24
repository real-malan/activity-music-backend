package pl.activitymusic.backend.business.spotify.control

import org.springframework.stereotype.Service
import pl.activitymusic.backend.business.common.entity.LocalDateTimeSpan
import pl.activitymusic.backend.business.spotify.boundary.SpotifyOperations
import pl.activitymusic.backend.business.spotify.entity.AuthorizationResponse
import pl.activitymusic.backend.business.spotify.entity.TokensResponse
import pl.activitymusic.backend.business.spotify.entity.Track
import pl.activitymusic.backend.business.spotify.entity.UserProfileData


@Service
internal class DefaultSpotifyOperations(
    private val accountsOperations: SpotifyAccountsOperations
) : SpotifyOperations {

    override fun getAuthorizationUrl(): String {
        return accountsOperations.getAuthorizationUrl()
    }

    override fun getTokens(response: AuthorizationResponse): TokensResponse {
        return accountsOperations.getTokens(response)
    }

    override fun getTokens(refreshToken: String): TokensResponse {
        return accountsOperations.getTokens(refreshToken)
    }

    override fun getUserProfile(): UserProfileData {
        TODO("Not yet implemented")
    }

    override fun getTracks(span: LocalDateTimeSpan): List<Track> {
        TODO("Not yet implemented")
    }
}
