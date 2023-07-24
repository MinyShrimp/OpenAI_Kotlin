package shrimp.openai_api.security.dto

data class LoginRequest(
    val email: String,
    val password: String
)
