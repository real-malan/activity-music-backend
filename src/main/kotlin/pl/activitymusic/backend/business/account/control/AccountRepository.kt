package pl.activitymusic.backend.business.account.control

import org.springframework.data.jpa.repository.JpaRepository
import pl.activitymusic.backend.business.account.entity.Account

interface AccountRepository: JpaRepository<Account, Long> {

    fun findByEmail(email: String): Account?
}
