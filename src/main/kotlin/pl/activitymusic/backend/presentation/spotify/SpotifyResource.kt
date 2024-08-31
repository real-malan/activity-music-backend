package pl.activitymusic.backend.presentation.spotify

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.activitymusic.backend.business.spotify.boundary.SpotifyOperations
import pl.activitymusic.backend.business.spotify.entity.AuthorizationResponse
import pl.activitymusic.backend.presentation.Endpoints

@RestController
class SpotifyResource(
    private val operations: SpotifyOperations
) {

    companion object {
        const val SPOTIFY_EXTERNAL_API = "${Endpoints.EXTERNAL_API_URI}/spotify"
        private const val SPOTIFY_API = "${Endpoints.API_URI}/spotify"
    }

    @GetMapping("$SPOTIFY_API/authorize")
    fun getAuthorizationUrl(): String {
        return operations.getAuthorizationUrl()
    }

    @GetMapping("$SPOTIFY_EXTERNAL_API/authorization-callback")
    fun authorizationCallback(response: AuthorizationResponse) {
        println(operations.getTokens(response))
    }

    @GetMapping("$SPOTIFY_EXTERNAL_API/token-callback")
    fun tokenCallback() {
        println("Token callback")
    }
}
