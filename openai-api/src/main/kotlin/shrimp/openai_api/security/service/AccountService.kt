package shrimp.openai_api.security.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import shrimp.openai_api.security.entity.Account
import shrimp.openai_api.security.repository.AccountRepository

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    fun saveUser(
        account: Account
    ): Account {
        account.password = this.passwordEncoder.encode(account.password)
        return this.accountRepository.save(account)
    }

    fun loginUser(
        account: Account
    ): Account {
        TODO()
    }

    override fun loadUserByUsername(
        username: String
    ): UserDetails {
        return this.accountRepository.findByEmail(username)?.getAuthorities()
            ?: throw UsernameNotFoundException("$username Can Not Found")
    }
}
