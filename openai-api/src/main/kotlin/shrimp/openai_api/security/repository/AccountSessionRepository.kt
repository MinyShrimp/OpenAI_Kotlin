package shrimp.openai_api.security.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import shrimp.openai_api.security.entity.Account
import shrimp.openai_api.security.entity.AccountSession
import java.util.*

@Repository
interface AccountSessionRepository : JpaRepository<AccountSession, UUID> {
    fun findByToken(token: String): AccountSession?

    fun findByAccount(account: Account): AccountSession?
}
