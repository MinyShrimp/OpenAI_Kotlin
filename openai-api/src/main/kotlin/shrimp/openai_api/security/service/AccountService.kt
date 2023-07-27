package shrimp.openai_api.security.service

import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import shrimp.openai_api.security.dto.error.ErrorCode
import shrimp.openai_api.security.dto.request.LoginRequest
import shrimp.openai_api.security.entity.Account
import shrimp.openai_api.security.exception.AuthenticationException
import shrimp.openai_api.security.repository.AccountRepository
import java.time.LocalDateTime

@Service
class AccountService(
    private val passwordEncoder: PasswordEncoder,
    private val accountRepository: AccountRepository,
    private val accountSessionService: AccountSessionService
) {

    fun saveAccount(
        account: Account
    ): Account {
        val isExists = this.accountRepository.existsByEmail(account.email)
        if (isExists) throw AuthenticationException(ErrorCode.ExistEmail)

        account.password = this.passwordEncoder.encode(account.password)
        return this.accountRepository.save(account)
    }

    @Transactional
    fun loginAccount(
        dto: LoginRequest
    ): Account {
        val isExists = this.accountRepository.existsByEmail(dto.email)
        if (!isExists) throw AuthenticationException(ErrorCode.NoExistEmail)

        val savedAccount = this.accountRepository.findByEmail(dto.email)!!
        val isMatch = this.passwordEncoder.matches(dto.password, savedAccount.password)
        if (!isMatch) throw AuthenticationException(ErrorCode.NoMatchPassword)

        savedAccount.isLogin = true
        savedAccount.loginAt = LocalDateTime.now()
        savedAccount.session = this.accountSessionService.saveSession(savedAccount)
        return this.accountRepository.save(savedAccount)
    }

    fun logoutAccount(
        token: String
    ): Account {
        val account = this.accountSessionService.getAccountByToken(token)
        account.isLogin = false
        account.logoutAt = LocalDateTime.now()
        account.session = null

        this.accountSessionService.deleteSession(account)
        return this.accountRepository.save(account)
    }
}
