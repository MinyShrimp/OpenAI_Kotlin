package shrimp.openai_core.api.completion.response

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import shrimp.openai_core.api.completion.entity.Logprobs
import shrimp.openai_core.api.completion.entity.Usage

/**
 * Completions API Response DTO
 *
 * @author 김회민
 * @see <a href="https://platform.openai.com/docs/api-reference/completions">Completions API Document</a>
 * @since 2023-03-26
 */
data class CompletionResponse @JsonCreator constructor(
    @JsonProperty("id") val id: String? = null,
    @JsonProperty("object") val obj: String? = null,
    @JsonProperty("model") val model: String? = null,
    @JsonProperty("created") val created: Long? = null,
    @JsonProperty("usage") val usage: Usage? = null,
    @JsonProperty("choices") val choices: List<Choice>? = null
) {
    data class Choice @JsonCreator constructor(
        @JsonProperty("index") val index: Int? = null,
        @JsonProperty("text") val text: String? = null,
        @JsonProperty("logprobs") val logprobs: Logprobs? = null,
        @JsonProperty("finish_reason") val finishReason: String? = null,
    )
}
