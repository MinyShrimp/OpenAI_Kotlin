package shrimp.openai_api.context.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import shrimp.openai_api.context.entity.Context
import shrimp.openai_api.security.entity.Account
import java.util.*

data class CreateContextRequest(
    val title: String,
    val model: String,
    val description: String?,
    @JsonProperty("pre_prompt_list")
    val prePromptList: List<PrePromptDto>,
) {
    fun convertEntity(
        account: Account
    ): Context {
        return Context(
            title = this.title,
            model = this.model,
            description = this.description,
            account = account,
        )
    }
}
