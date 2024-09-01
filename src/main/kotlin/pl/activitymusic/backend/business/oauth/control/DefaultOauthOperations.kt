package pl.activitymusic.backend.business.oauth.control

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestClient
import org.springframework.web.util.UriComponents
import org.springframework.web.util.UriComponentsBuilder
import pl.activitymusic.backend.business.oauth.boundary.OauthOperations
import pl.activitymusic.backend.business.oauth.entity.*
import pl.activitymusic.backend.business.oauth.entity.ParameterName.*

@Service
internal class DefaultOauthOperations(
    @Value("\${application.url}") private val applicationUrl: String
) : OauthOperations {

    companion object {
        private const val CODE_RESPONSE_TYPE = "code"
        private const val AUTHORIZATION_CODE_GRANT = "authorization_code"
    }

    override fun getAuthorizationUrl(data: CreateAuthorizationUrlData): String {
        return getAuthorizationUrlComponents(data).toUriString()
    }

    override fun getAuthorizationUrlComponents(data: CreateAuthorizationUrlData): UriComponents {
        return UriComponentsBuilder.fromHttpUrl(data.properties.authorizeUrl)
            .queryParam(RESPONSE_TYPE.lowercase(), CODE_RESPONSE_TYPE)
            .queryParam(CLIENT_ID.lowercase(), data.properties.client.id)
            .queryParam(SCOPE.lowercase(), data.scope)
            .queryParam(REDIRECT_URI.lowercase(), "${applicationUrl}${data.callbackUrl}")
            .queryParam(STATE.lowercase(), getState())
            .build(true)
    }

    override fun getTokens(data: GetTokensData): TokensResponse {
        val response: AuthorizationResponse = data.authorizationResponse

        if (response.state != getState()) {
            throw IllegalStateException("Received state '${response.state}' is not correct")
        }

        if (response.error != null) {
            throw IllegalStateException("Error occurred '${response.error}' when authenticating")
        }

        if (response.code == null) {
            throw IllegalStateException("Authentication code is null")
        }

        val request = LinkedMultiValueMap<String, String>().apply {
            data.additionalRequestParameters.forEach { add(it.key, it.value) }
            add(CODE.lowercase(), response.code)
            add(GRANT_TYPE.lowercase(), AUTHORIZATION_CODE_GRANT)
        }

        return RestClient.create(data.properties.tokensUrl)
            .post()
            .headers(data.headersConsumer)
            .body(request)
            .retrieve()
            .body(TokensResponse::class.java)!!
    }

    private fun getState(): String {
        return "TODO"
    }
}
