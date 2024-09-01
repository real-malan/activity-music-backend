package pl.activitymusic.backend.business.oauth.entity

enum class ParameterName {
    RESPONSE_TYPE,
    CLIENT_ID,
    CLIENT_SECRET,
    SCOPE,
    REDIRECT_URI,
    STATE,
    CODE,
    GRANT_TYPE;

    fun lowercase(): String {
        return this.name.lowercase()
    }
}
