package shrimp.openai_api.security.exception

class AuthenticationException(
    message: String = "",
    cause: Throwable? = null
) : RuntimeException(message, cause) {
}
