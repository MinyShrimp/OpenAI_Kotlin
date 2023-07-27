package shrimp.openai_api.security.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import shrimp.openai_api.security.dto.error.ErrorCode
import shrimp.openai_api.security.entity.Account
import shrimp.openai_api.security.entity.AccountSession
import shrimp.openai_api.security.exception.AuthenticationException
import shrimp.openai_api.security.exception.AuthorizationException
import shrimp.openai_api.security.repository.AccountSessionRepository

@Service
class AccountSessionService(
    private val cookieService: CookieService,
    private val accountSessionRepository: AccountSessionRepository
) {
    @Transactional
    fun saveSession(
        account: Account
    ): AccountSession {
        val token = this.cookieService.generateToken()

        val session = this.accountSessionRepository.findByAccount(account)
        if (session != null) {
            session.token = token
            return this.accountSessionRepository.save(session)
        }

        return this.accountSessionRepository.save(
            AccountSession(account = account, token = token)
        )
    }

    @Transactional
    fun deleteSession(
        account: Account
    ) {
        val session = this.accountSessionRepository.findByAccount(account)
            ?: throw AuthenticationException(ErrorCode.AlreadyLogout)

        this.accountSessionRepository.delete(session)
    }

    fun getAccountByToken(
        token: String
    ): Account {
        val session = this.accountSessionRepository.findByToken(token)
            ?: throw AuthorizationException(ErrorCode.AlreadyLogout)

        return session.account
    }
}
