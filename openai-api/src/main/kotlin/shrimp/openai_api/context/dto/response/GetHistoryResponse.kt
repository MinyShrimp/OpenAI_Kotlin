package shrimp.openai_api.context.dto.response

import shrimp.openai_api.context.entity.History
import shrimp.openai_api.context.types.Name
import shrimp.openai_api.context.types.Role
import java.time.LocalDateTime

data class GetHistoryResponse(
    val role: Role,
    val name: Name?,
    val content: String,
    val createAt: LocalDateTime
) {
    companion object {
        fun of(
            history: History
        ): GetHistoryResponse {
            return GetHistoryResponse(
                role = history.role,
                name = history.name,
                content = history.content,
                createAt = history.createAt
            )
        }
    }
}