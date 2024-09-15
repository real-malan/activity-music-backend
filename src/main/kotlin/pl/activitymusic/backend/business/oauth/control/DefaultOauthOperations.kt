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
import pl.activitymusic.backend.system.crypto.CryptoOperations

@Service
internal class DefaultOauthOperations(
    @Value("\${application.url}") private val applicationUrl: String,
    private val cryptoOperations: CryptoOperations
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
            .queryParam(STATE.lowercase(), cryptoOperations.encrypt(data.properties.state))
            .build()
            .encode()
    }

    override fun getTokens(data: GetTokensData): TokensResponse {
        val response: AuthorizationResponse = data.authorizationResponse
        val state: String = cryptoOperations.decrypt(response.state)

        if (state != data.properties.state) {
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
}
