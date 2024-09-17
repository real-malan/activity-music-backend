package pl.activitymusic.backend.business.account.entity

import java.time.Instant

data class Service(
    val id: Long,
    val account: Account,
    val externalId: String,
    val source: Source,
    val accessToken: String,
    val accessTokenExpiresAt: Instant,
    val refreshToken: String,
    val modifiedAt: Instant,
    val createdAt: Instant
)
