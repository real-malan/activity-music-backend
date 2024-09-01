package pl.activitymusic.backend.business.strava.entity

import pl.activitymusic.backend.business.oauth.entity.AuthorizationResponse

class StravaAuthorizationResponse(
    code: String?,
    error: String?,
    state: String,
    val scope: String
): AuthorizationResponse(code, error, state)
