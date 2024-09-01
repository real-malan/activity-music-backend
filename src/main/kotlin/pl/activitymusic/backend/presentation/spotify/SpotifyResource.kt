package pl.activitymusic.backend.presentation.spotify

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.activitymusic.backend.business.account.boundary.AccountOperations
import pl.activitymusic.backend.business.account.entity.Account
import pl.activitymusic.backend.business.account.entity.Source
import pl.activitymusic.backend.business.spotify.boundary.SpotifyOperations
import pl.activitymusic.backend.business.oauth.entity.AuthorizationResponse
import pl.activitymusic.backend.business.oauth.entity.TokensResponse
import pl.activitymusic.backend.business.spotify.entity.UserProfileData
import pl.activitymusic.backend.presentation.Endpoints

@RestController
class SpotifyResource(
    private val spotifyOperations: SpotifyOperations,
    private val accountOperations: AccountOperations
) {

    companion object {
        const val SPOTIFY_EXTERNAL_API = "${Endpoints.EXTERNAL_API_URI}/spotify"
        private const val SPOTIFY_API = "${Endpoints.API_URI}/spotify"
    }

    @GetMapping("$SPOTIFY_API/authorize")
    fun getAuthorizationUrl(): String {
        return spotifyOperations.getAuthorizationUrl()
    }

    @GetMapping("$SPOTIFY_EXTERNAL_API/authorization-callback")
    fun authorizationCallback(response: AuthorizationResponse) {
        val tokens: TokensResponse = spotifyOperations.getTokens(response)
        val profile: UserProfileData = spotifyOperations.getUserProfile(tokens.accessToken)
        val account = Account(
            email = profile.email,
            source = Source.SPOTIFY,
            accessToken = tokens.accessToken,
            refreshToken = tokens.refreshToken
        )
        println(accountOperations.createOrUpdateAccount(account))
    }
}
