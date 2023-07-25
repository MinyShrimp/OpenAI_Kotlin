package shrimp.openai_api.security.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import shrimp.openai_api.security.exception.AuthenticationException
import shrimp.openai_api.security.service.AccountSessionService
import shrimp.openai_api.security.service.CookieService

class AuthTokenFilter(
    private val accountSessionService: AccountSessionService
) : Filter {
    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        val req = request as HttpServletRequest

        val cookie = req.cookies.find { it.name == CookieService.COOKIE_NAME }
        if (cookie == null) throw AuthenticationException("Not Exists Cookie")

        val account = this.accountSessionService.getAccountByToken(cookie.value)
        if (!account.isLogin) throw AuthenticationException("Not Login")

        chain.doFilter(request, response)
    }
}
