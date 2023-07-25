package shrimp.openai_api.context.dto.response

import shrimp.openai_api.context.entity.Prompt
import shrimp.openai_api.context.types.Name
import shrimp.openai_api.context.types.Role

data class GetPromptResponse(
    val order: Int,
    val role: Role,
    val name: Name?,
    val content: String
) {
    companion object {
        fun of(
            prompt: Prompt
        ): GetPromptResponse {
            return GetPromptResponse(
                order = prompt.order,
                role = prompt.role,
                name = prompt.name,
                content = prompt.content
            )
        }
    }
}
