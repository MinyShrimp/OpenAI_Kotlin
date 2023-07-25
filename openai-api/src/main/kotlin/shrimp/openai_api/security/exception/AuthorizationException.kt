package shrimp.openai_api.security.exception

/**
 * 인가 정보 예외
 * - 토큰 관련
 */
class AuthorizationException(
    message: String = "",
    cause: Throwable? = null
) : RuntimeException(message, cause)
