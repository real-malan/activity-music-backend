package pl.activitymusic.backend.business.spotify.entity

import java.time.Duration
import java.time.ZonedDateTime

data class Track (
    val id: String,
    val name: String,
    val artists: Set<Artist>,
    val duration: Duration,
    val popularity: Int,
    val playedAt: ZonedDateTime
)
