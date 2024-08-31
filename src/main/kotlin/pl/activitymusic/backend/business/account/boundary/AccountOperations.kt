package pl.activitymusic.backend.business.account.boundary

import pl.activitymusic.backend.business.account.entity.Account

interface AccountOperations {

    fun createOrUpdateAccount(account: Account): Account
}
