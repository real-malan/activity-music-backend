package pl.activitymusic.backend.business.oauth.entity

open class AuthorizationResponse(
    val code: String?,
    val error: String?,
    val state: String
)
