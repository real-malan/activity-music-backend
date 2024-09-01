package pl.activitymusic.backend.business.oauth.boundary

import org.springframework.web.util.UriComponents
import pl.activitymusic.backend.business.oauth.entity.CreateAuthorizationUrlData
import pl.activitymusic.backend.business.oauth.entity.GetTokensData
import pl.activitymusic.backend.business.oauth.entity.TokensResponse

interface OauthOperations {
    fun getAuthorizationUrl(data: CreateAuthorizationUrlData): String

    fun getAuthorizationUrlComponents(data: CreateAuthorizationUrlData): UriComponents

    fun getTokens(data: GetTokensData): TokensResponse
}
