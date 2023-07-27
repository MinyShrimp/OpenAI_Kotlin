package shrimp.openai_api.security.controller

import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import mu.KotlinLogging
import org.springframework.web.bind.annotation.*
import shrimp.openai_api.security.dto.request.LoginRequest
import shrimp.openai_api.security.dto.request.SignupRequest
import shrimp.openai_api.security.dto.response.AccountResponse
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
        @RequestBody @Valid dto: SignupRequest
    ): AccountResponse {
        val savedUser = this.accountService.saveAccount(dto.convertEntity())
        return AccountResponse.of(savedUser)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody @Valid dto: LoginRequest,
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
