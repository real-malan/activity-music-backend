package pl.activitymusic.backend.business.account.entity

import java.time.Instant

data class Account (
    val id: Long,
    val services: List<Service>,
    val updatedAt: Instant,
    val createdAt: Instant
)
