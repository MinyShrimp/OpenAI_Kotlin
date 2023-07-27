package shrimp.openai_api.security.exception

import shrimp.openai_api.security.dto.error.ErrorCode

/**
 * 인가 정보 예외
 * - 토큰 관련
 */
class AuthorizationException(
    val code: ErrorCode,
    cause: Throwable? = null
) : RuntimeException(code.message, cause)
