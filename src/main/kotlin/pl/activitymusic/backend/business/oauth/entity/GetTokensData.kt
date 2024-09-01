package pl.activitymusic.backend.business.oauth.entity

import org.springframework.http.HttpHeaders

data class GetTokensData (
    val properties: OauthProperties,
    val authorizationResponse: AuthorizationResponse,
    val headersConsumer: (HttpHeaders) -> Unit = {},
    val additionalRequestParameters: Map<String, String> = emptyMap(),
)
