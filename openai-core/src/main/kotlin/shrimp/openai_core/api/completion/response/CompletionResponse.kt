package shrimp.openai_core.api.completion.response

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

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

    data class Usage @JsonCreator constructor(
        @JsonProperty("completion_tokens") val completionTokens: Long? = null,
        @JsonProperty("prompt_tokens") val promptTokens: Long? = null,
        @JsonProperty("total_tokens") val totalTokens: Long? = null,
    )

    data class Choice @JsonCreator constructor(
        @JsonProperty("text") val text: String? = null,
        @JsonProperty("message") val message: Message? = null,
        @JsonProperty("delta") val delta: Message? = null,
        @JsonProperty("index") val index: Int? = null,
        @JsonProperty("logprobs") val logprobs: Logprobs? = null,
        @JsonProperty("finish_reason") val finishReason: String? = null,
    ) {
        data class Message @JsonCreator constructor(
            @JsonProperty("role") val role: String? = null,
            @JsonProperty("content") val content: String? = null
        )

        data class Logprobs @JsonCreator constructor(
            @JsonProperty("tokens") val tokens: List<String>? = null,
            @JsonProperty("token_logprobs") val tokenLogprobs: List<Double>? = null,
            @JsonProperty("top_logprobs") val topLogprobs: List<Map<String, Double>>? = null,
            @JsonProperty("text_offset") val textOffset: List<Int>? = null,
        )
    }
}
