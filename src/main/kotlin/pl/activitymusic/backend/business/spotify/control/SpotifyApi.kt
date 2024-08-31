package pl.activitymusic.backend.business.spotify.control

import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.service.annotation.GetExchange
import pl.activitymusic.backend.business.spotify.entity.UserProfileData

internal interface SpotifyApi {

    @GetExchange("/me")
    fun getProfile(@RequestHeader(name = AUTHORIZATION) authorizationHeaderValue: String): UserProfileData
}
