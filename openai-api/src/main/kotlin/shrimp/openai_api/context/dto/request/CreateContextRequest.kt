package shrimp.openai_api.context.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import shrimp.openai_api.context.entity.Context
import shrimp.openai_api.context.entity.Prompt
import shrimp.openai_api.context.types.Name
import shrimp.openai_api.context.types.Role
import java.util.*

data class CreateContextRequest(
    val id: UUID,
    val title: String,
    val model: String,
    val description: String?,
    @JsonProperty("pre_prompt_list")
    val prePromptList: List<PrePromptDTO>,
) {
    data class PrePromptDTO(
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

    fun convertEntity(): Context {
        return Context(
            id = this.id,
            title = this.title,
            model = this.model,
            description = this.description,
        )
    }
}