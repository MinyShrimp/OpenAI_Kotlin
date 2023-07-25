package shrimp.openai_api.context.dto.response

import shrimp.openai_api.context.entity.Context
import java.time.LocalDateTime
import java.util.*

data class ContextResponse(
    val id: UUID,
    val model: String,
    val title: String,
    val description: String?,
    val updateAt: LocalDateTime
) {
    companion object {
        fun of(
            context: Context
        ): ContextResponse {
            return ContextResponse(
                id = context.id,
                model = context.model,
                title = context.title,
                description = context.description,
                updateAt = context.updateAt
            )
        }
    }
}
