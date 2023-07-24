package shrimp.openai_api.security.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import shrimp.openai_api.security.dto.AccountResponse
import shrimp.openai_api.security.dto.LoginRequest
import shrimp.openai_api.security.dto.SignupRequest
import shrimp.openai_api.security.entity.Account
import shrimp.openai_api.security.service.AccountService

@RestController
@RequestMapping("/auth")
class SecurityController(
    private val accountService: AccountService
) {
    @PostMapping("/signup")
    fun signup(
        @RequestBody dto: SignupRequest
    ): AccountResponse {
        val savedUser = this.accountService.saveUser(dto.convertEntity())
        return AccountResponse.of(savedUser)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody dto: LoginRequest
    ): AccountResponse {
        TODO("Login Function Not Implemented")
    }

    @PostMapping("/logout")
    fun logout(
        @RequestBody account: Account
    ): AccountResponse {
        TODO("Logout Function Not Implemented")
    }
}
