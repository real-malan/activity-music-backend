package pl.activitymusic.backend.business.oauth.entity

data class CreateAuthorizationUrlData (
    val properties: OauthProperties,
    val scope: String,
    val callbackUrl: String
)
