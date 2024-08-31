package pl.activitymusic.backend.business.spotify.control

import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.PostExchange
import pl.activitymusic.backend.business.spotify.entity.Tokens

internal interface SpotifyAccountsApi {

    @PostExchange("/token")
    fun getTokens(@RequestParam request: MultiValueMap<String, String>): Tokens

}
