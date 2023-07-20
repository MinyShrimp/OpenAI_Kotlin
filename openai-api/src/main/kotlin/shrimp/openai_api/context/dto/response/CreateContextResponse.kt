package shrimp.openai_api.context.dto.response

import shrimp.openai_api.context.entity.Context
import java.time.LocalDateTime
import java.util.*

data class CreateContextResponse(
    val id: UUID,
    val title: String,
    val description: String,
    val updateAt: LocalDateTime,
    val prePromptList: List<GetPromptResponse>
) {
    companion object {
        fun of(
            context: Context
        ): CreateContextResponse {
            return CreateContextResponse(
                id = context.id,
                title = context.title,
                description = context.description,
                updateAt = context.updateAt,
                prePromptList = context.prePromptList.map { GetPromptResponse.of(it) }
            )
        }
    }
}
