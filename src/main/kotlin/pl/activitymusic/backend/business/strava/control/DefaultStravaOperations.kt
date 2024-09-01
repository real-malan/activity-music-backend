package pl.activitymusic.backend.business.strava.control

import org.springframework.stereotype.Service
import pl.activitymusic.backend.business.oauth.boundary.OauthOperations
import pl.activitymusic.backend.business.oauth.entity.CreateAuthorizationUrlData
import pl.activitymusic.backend.business.oauth.entity.GetTokensData
import pl.activitymusic.backend.business.oauth.entity.ParameterName
import pl.activitymusic.backend.business.oauth.entity.TokensResponse
import pl.activitymusic.backend.business.strava.boundary.StravaOperations
import pl.activitymusic.backend.business.strava.entity.Scope
import pl.activitymusic.backend.business.strava.entity.StravaAuthorizationResponse
import pl.activitymusic.backend.presentation.strava.StravaResource

@Service
internal class DefaultStravaOperations(
    private val properties: StravaProperties,
    private val oauthOperations: OauthOperations
) : StravaOperations {

    override fun getAuthorizationUrl(): String {
        return oauthOperations.getAuthorizationUrl(CreateAuthorizationUrlData(
            properties = properties.oauth,
            scope = listOf(Scope.ACTIVITY_READ, Scope.ACTIVITY_READ_ALL, Scope.ACTIVITY_WRITE).joinToString(","),
            callbackUrl = "${StravaResource.STRAVA_EXTERNAL_API}/authorization-callback"
        ))
    }

    override fun getTokens(response: StravaAuthorizationResponse): TokensResponse {
        return oauthOperations.getTokens(GetTokensData(
            properties = properties.oauth,
            authorizationResponse = response,
            additionalRequestParameters = properties.oauth.client.let { client -> mapOf(
                Pair(ParameterName.CLIENT_ID.lowercase(), client.id),
                Pair(ParameterName.CLIENT_SECRET.lowercase(), client.secret)
            ) }
        ))
    }
}
