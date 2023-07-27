package shrimp.openai_api.security.exception

import shrimp.openai_api.security.dto.error.ErrorCode

/**
 * 인증 실패
 * - 로그인, 로그아웃, 회원 가입
 */
class AuthenticationException(
    val code: ErrorCode,
    cause: Throwable? = null
) : RuntimeException(code.message, cause)
