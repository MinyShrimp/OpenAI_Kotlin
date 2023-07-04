package shrimp.openai_core.api.completion.entity

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class Usage @JsonCreator constructor(
    @JsonProperty("completion_tokens") val completionTokens: Long? = null,
    @JsonProperty("prompt_tokens") val promptTokens: Long? = null,
    @JsonProperty("total_tokens") val totalTokens: Long? = null,
)
