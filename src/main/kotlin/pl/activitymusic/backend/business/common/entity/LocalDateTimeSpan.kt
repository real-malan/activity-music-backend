package pl.activitymusic.backend.business.common.entity

import java.time.LocalDateTime

data class LocalDateTimeSpan (
    val from: LocalDateTime,
    val to: LocalDateTime
)
