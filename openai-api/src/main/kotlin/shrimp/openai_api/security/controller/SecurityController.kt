package shrimp.openai_api.security.controller

import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.web.bind.annotation.*
import shrimp.openai_api.security.dto.AccountResponse
import shrimp.openai_api.security.dto.LoginRequest
import shrimp.openai_api.security.dto.SignupRequest
import shrimp.openai_api.security.service.AccountService
import shrimp.openai_api.security.service.CookieService

@RestController
@RequestMapping("/auth")
class SecurityController(
    private val cookieService: CookieService,
    private val accountService: AccountService
) {
    private val logger = KotlinLogging.logger {}

    @PostMapping("/signup")
    fun signup(
        @RequestBody dto: SignupRequest
    ): AccountResponse {
        val savedUser = this.accountService.saveAccount(dto.convertEntity())
        return AccountResponse.of(savedUser)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody dto: LoginRequest,
        response: HttpServletResponse
    ): AccountResponse {
        val account = this.accountService.loginAccount(dto)
        val cookie = this.cookieService.getCookie(account.session!!.token)

        response.addCookie(cookie)
        return AccountResponse.of(account)
    }

    @PostMapping("/logout")
    fun logout(
        @CookieValue(CookieService.COOKIE_NAME) token: String,
        response: HttpServletResponse
    ) {
        val account = this.accountService.logoutAccount(token)
        response.addCookie(this.cookieService.deleteCookie())
    }
}
