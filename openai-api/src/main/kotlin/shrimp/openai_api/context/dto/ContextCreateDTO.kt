package shrimp.openai_api.context.dto

import com.fasterxml.jackson.annotation.JsonProperty
import shrimp.openai_api.context.entity.Context
import shrimp.openai_api.context.entity.Prompt
import shrimp.openai_api.context.types.Name
import shrimp.openai_api.context.types.Role
import java.util.*

class ContextCreateDTO(
    val id: UUID,
    val title: String,
    val description: String,
    @JsonProperty("pre_prompt_list")
    val prePromptList: List<PrePromptDTO>,
) {
    class PrePromptDTO(
        val role: String,
        val name: String?,
        val content: String,
    ) {
        fun convertEntity(
            context: Context
        ): Prompt {
            return Prompt(
                role = enumValueOf<Role>(this.role),
                name = if (this.name != null) enumValueOf<Name>(this.name) else Name.SYSTEM,
                content = this.content,
                context = context
            )
        }
    }

    fun convertEntity(): Context {
        return Context(
            id = this.id,
            title = this.title,
            description = this.description,
        )
    }
}