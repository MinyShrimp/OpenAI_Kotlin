package shrimp.openai_api.security.service

import jakarta.servlet.http.Cookie
import org.apache.catalina.util.StandardSessionIdGenerator
import org.springframework.stereotype.Service

@Service
class CookieService {
    companion object {
        const val COOKIE_NAME = "AUTH_TOKEN"
    }

    private val generator = StandardSessionIdGenerator()

    fun generateToken(): String {
        return this.generator.generateSessionId()
    }

    private fun setHttpCookie(
        cookie: Cookie
    ): Cookie {
        return cookie.apply {
            path = "/"
            isHttpOnly = false
            secure = false
        }
    }

    private fun setHttpsCookie(
        cookie: Cookie
    ): Cookie {
        return cookie.apply {
            path = "/"
            isHttpOnly = true
            secure = true
            setAttribute("SameSite", "None")
        }
    }

    fun getCookie(
        token: String = this.generateToken()
    ): Cookie {
        val cookie = Cookie(COOKIE_NAME, token)
        return setHttpCookie(cookie)
    }

    fun deleteCookie(): Cookie {
        val cookie = Cookie(COOKIE_NAME, "")
        return setHttpCookie(cookie).apply { maxAge = 0 }
    }
}
