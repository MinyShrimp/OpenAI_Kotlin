package shrimp.openai_api.security.dto.error

import java.time.LocalDateTime

data class ErrorResponse(
    val status: Int,
    val code: Int,
    val messages: List<String>
) {
    val timestamp: LocalDateTime = LocalDateTime.now()
}
