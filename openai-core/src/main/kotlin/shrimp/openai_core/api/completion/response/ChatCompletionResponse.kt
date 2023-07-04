package shrimp.openai_core.api.completion.response

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import shrimp.openai_core.api.completion.entity.*

/**
 * Completions API Response DTO
 *
 * @author 김회민
 * @see <a href="https://platform.openai.com/docs/api-reference/completions">Completions API Document</a>
 * @since 2023-03-26
 */
data class ChatCompletionResponse @JsonCreator constructor(
    @JsonProperty("id") val id: String? = null,
    @JsonProperty("object") val obj: String? = null,
    @JsonProperty("model") val model: String? = null,
    @JsonProperty("created") val created: Long? = null,
    @JsonProperty("usage") val usage: Usage? = null,
    @JsonProperty("choices") val choices: List<Choice>? = null
) {
    data class Choice @JsonCreator constructor(
        @JsonProperty("index") val index: Int? = null,
        @JsonProperty("message") val message: Message? = null,
        @JsonProperty("delta") val delta: Message? = null,
        @JsonProperty("logprobs") val logprobs: Logprobs? = null,
        @JsonProperty("finish_reason") val finishReason: String? = null,
    ) {
        data class Message @JsonCreator constructor(
            @JsonProperty("role") val role: Role = Role.USER,
            @JsonProperty("content") val content: String? = null,
            @JsonProperty("name") val name: String? = null,
            @JsonProperty("function_call") val functionCall: FunctionCall? = null
        )
    }
}
