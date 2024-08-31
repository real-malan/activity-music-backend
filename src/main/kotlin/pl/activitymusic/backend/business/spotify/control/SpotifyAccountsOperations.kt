package pl.activitymusic.backend.business.spotify.control

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.util.UriComponentsBuilder
import pl.activitymusic.backend.business.spotify.entity.AuthorizationResponse
import pl.activitymusic.backend.business.spotify.entity.Scope
import pl.activitymusic.backend.business.spotify.entity.TokensResponse
import pl.activitymusic.backend.presentation.spotify.SpotifyResource

@Component
internal class SpotifyAccountsOperations(
    private val properties: SpotifyProperties,
    @Value("\${application.url}") private val applicationUrl: String,
    private val accountsApi: SpotifyAccountsApi
) {

    fun getAuthorizationUrl(): String {
        return UriComponentsBuilder.fromHttpUrl(properties.accountsUrl)
            .path("authorize")
            .queryParam("response_type", "code")
            .queryParam("client_id", properties.client.id)
            .queryParam("scope", "${Scope.USER_READ_EMAIL} ${Scope.USER_READ_RECENTLY_PLAYED}")
            .queryParam("redirect_uri", getRedirectUri())
            .queryParam("state", getState())
            .toUriString()
    }

    fun getTokens(response: AuthorizationResponse): TokensResponse {
        if (response.state != getState()) throw IllegalStateException("Received state '${response.state}' is not matching")

        if (response.error != null) {
            throw IllegalStateException("Error occurred '${response.error}' when authenticating")
        }

        val request = LinkedMultiValueMap<String, String>().apply {
            add("code", response.code!!)
            add("redirect_uri", getRedirectUri())
            add("grant_type", "authorization_code")
        }

        return accountsApi.getTokens(request)
    }

    fun getTokens(refreshToken: String): TokensResponse {
        TODO("Not yet implemented")
    }

    private fun getRedirectUri(): String {
        return "${applicationUrl}${SpotifyResource.SPOTIFY_EXTERNAL_API}/authorization-callback"
    }

    private fun getState(): String {
        return "TODO"
    }
}
