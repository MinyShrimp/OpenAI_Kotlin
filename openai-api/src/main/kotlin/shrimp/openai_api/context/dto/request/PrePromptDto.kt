package shrimp.openai_api.context.dto.request

import shrimp.openai_api.context.entity.Context
import shrimp.openai_api.context.entity.Prompt
import shrimp.openai_api.context.types.Name
import shrimp.openai_api.context.types.Role

data class PrePromptDto(
    val role: Role,
    val name: Name?,
    val content: String,
) {
    fun convertEntity(
        order: Int,
        context: Context,
    ): Prompt {
        return Prompt(
            role = this.role,
            name = this.name ?: Name.SYSTEM,
            content = this.content,
            context = context,
            order = order
        )
    }
}