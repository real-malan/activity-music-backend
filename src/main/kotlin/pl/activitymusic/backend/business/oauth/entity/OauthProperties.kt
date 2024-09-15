package pl.activitymusic.backend.business.oauth.entity

import java.util.*

data class OauthProperties(
    val authorizeUrl: String,
    val tokensUrl: String,
    val state: String,
    val client: Client
) {
    data class Client(
        val id: String,
        val secret: String
    ) {
        fun getAuthorizationHeaderValue(): String {
            val client = "${id}:${secret}"
            val clientBase64Encoded = Base64.getEncoder().encodeToString(client.toByteArray())

            return "Basic $clientBase64Encoded"
        }
    }
}
