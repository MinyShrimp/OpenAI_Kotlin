package shrimp.openai_core.api.completion.response

import com.fasterxml.jackson.annotation.JsonProperty
import shrimp.openai_core.api.completion.entity.*

/**
 * Completions API Response DTO
 *
 * @author 김회민
 * @see <a href="https://platform.openai.com/docs/api-reference/completions">Completions API Document</a>
 * @since 2023-03-26
 */
data class ChatCompletionResponse(
    val id: String? = null,
    @JsonProperty("object") val obj: String? = null,
    val model: String? = null,
    val created: Long? = null,
    val usage: Usage? = null,
    val choices: List<Choice>? = null
) {
    data class Choice(
        val index: Int? = null,
        val message: Message? = null,
        val delta: Message? = null,
        val logprobs: Logprobs? = null,
        val finishReason: String? = null,
    ) {
        data class Message(
            val role: Role = Role.USER,
            val content: String? = null,
            val name: String? = null,
            val functionCall: FunctionCall? = null
        )
    }
}
