package pl.activitymusic.backend.business.spotify.control

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import pl.activitymusic.backend.business.common.entity.LocalDateTimeSpan
import pl.activitymusic.backend.business.oauth.boundary.OauthOperations
import pl.activitymusic.backend.business.oauth.entity.*
import pl.activitymusic.backend.business.oauth.entity.ParameterName
import pl.activitymusic.backend.business.spotify.boundary.SpotifyOperations
import pl.activitymusic.backend.business.spotify.entity.Scope
import pl.activitymusic.backend.business.spotify.entity.Track
import pl.activitymusic.backend.business.spotify.entity.UserProfileData
import pl.activitymusic.backend.presentation.spotify.SpotifyResource


@Service
internal class DefaultSpotifyOperations(
    private val properties: SpotifyProperties,
    private val oauthOperations: OauthOperations,
    private val api: SpotifyApi
) : SpotifyOperations {

    override fun getAuthorizationUrl(): String {
        return oauthOperations.getAuthorizationUrl(getAuthorizationUrlData())
    }

    override fun getTokens(response: AuthorizationResponse): TokensResponse {
        val redirectUri: String = oauthOperations.getAuthorizationUrlComponents(getAuthorizationUrlData())
            .queryParams
            .getFirst(ParameterName.REDIRECT_URI.lowercase())!!

        return oauthOperations.getTokens(GetTokensData(
            properties = properties.oauth,
            authorizationResponse = response,
            headersConsumer = {
                it.set(HttpHeaders.AUTHORIZATION, properties.oauth.client.getAuthorizationHeaderValue())
                it.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            },
            additionalRequestParameters = mapOf(
                Pair(ParameterName.REDIRECT_URI.lowercase(), redirectUri)
            )
        ))
    }

    override fun getUserProfile(accessToken: String): UserProfileData {
        val authorizationHeader = "Bearer $accessToken"
        return api.getProfile(authorizationHeader)
    }

    override fun getTracks(span: LocalDateTimeSpan): List<Track> {
        TODO("Not yet implemented")
    }

    private fun getAuthorizationUrlData(): CreateAuthorizationUrlData {
        return CreateAuthorizationUrlData(
            properties = properties.oauth,
            scope = listOf(Scope.USER_READ_EMAIL, Scope.USER_READ_RECENTLY_PLAYED).joinToString(" "),
            callbackUrl = "${SpotifyResource.SPOTIFY_EXTERNAL_API}/authorization-callback"
        )
    }
}
