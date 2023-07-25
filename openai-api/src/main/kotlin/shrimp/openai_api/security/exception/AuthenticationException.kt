package shrimp.openai_api.security.exception

/**
 * 인증 실패
 * - 로그인, 로그아웃, 회원 가입
 */
class AuthenticationException(
    message: String = "",
    cause: Throwable? = null
) : RuntimeException(message, cause)
