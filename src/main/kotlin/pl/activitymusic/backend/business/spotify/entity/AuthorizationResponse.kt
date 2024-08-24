package pl.activitymusic.backend.business.spotify.entity

data class AuthorizationResponse(
    val code: String?,
    val error: String?,
    val state: String
)
