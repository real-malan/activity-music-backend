package pl.activitymusic.backend.business.account.control

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import pl.activitymusic.backend.business.account.boundary.AccountOperations
import pl.activitymusic.backend.business.account.entity.Account

private val logger = KotlinLogging.logger {}

@Service
internal class DefaultAccountOperations(
    private val repository: AccountRepository
) : AccountOperations {

    override fun createOrUpdateAccount(account: Account): Account {
        return repository.findByEmail(account.email)?.let {
            return repository.save(it.copy(
                source = account.source,
                accessToken = account.accessToken,
                refreshToken = account.refreshToken))
        } ?: repository.save(account).also {
            logger.info { "New account with id ${account.id} created" }
        }
    }
}
