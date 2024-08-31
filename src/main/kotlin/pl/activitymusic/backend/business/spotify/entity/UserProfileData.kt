package pl.activitymusic.backend.business.spotify.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class UserProfileData (
    val email: String,

    @JsonProperty("display_name")
    val name: String?
)
