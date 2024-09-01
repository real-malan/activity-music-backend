package pl.activitymusic.backend.business.oauth.entity

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class TokensResponse (
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Int
)
