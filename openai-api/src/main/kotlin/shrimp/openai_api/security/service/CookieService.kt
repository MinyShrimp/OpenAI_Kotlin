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

    fun getCookie(
        token: String = this.generateToken()
    ): Cookie {
        return Cookie(COOKIE_NAME, token)
            .apply {
                path = "/"
                isHttpOnly = true
                secure = true
                setAttribute("SameSite", "None")
            }
    }

    fun deleteCookie(): Cookie {
        return Cookie(COOKIE_NAME, "")
            .apply {
                path = "/"
                maxAge = 0
                isHttpOnly = true
                secure = true
                setAttribute("SameSite", "None")
            }
    }
}
