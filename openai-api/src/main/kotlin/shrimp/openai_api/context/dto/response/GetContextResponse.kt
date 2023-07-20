package shrimp.openai_api.context.dto.response

import shrimp.openai_api.context.entity.Context
import java.time.LocalDateTime
import java.util.*

data class GetContextResponse(
    val id: UUID,
    val title: String,
    val description: String,
    val updateAt: LocalDateTime
) {
    companion object {
        fun of(
            context: Context
        ): GetContextResponse {
            return GetContextResponse(
                id = context.id,
                title = context.title,
                description = context.description,
                updateAt = context.updateAt
            )
        }
    }
}