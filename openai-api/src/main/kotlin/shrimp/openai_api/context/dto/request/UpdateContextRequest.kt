package shrimp.openai_api.context.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class UpdateContextRequest(
    val id: UUID,
    val title: String,
    val model: String,
    val description: String?,
    @JsonProperty("pre_prompt_list")
    val prePromptList: List<PrePromptDto>,
)
