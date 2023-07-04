package shrimp.openai_core.api.completion.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import shrimp.openai_core.api.completion.entity.FunctionCall
import shrimp.openai_core.api.completion.entity.Role
import java.util.*

/**
 * Chat Completions API Request DTO
 * - POST /v1/chat/completions
 *
 * @author 김회민
 * @see <a href="https://platform.openai.com/docs/api-reference/chat">Chat API Document</a>
 * @since 2023-03-26
 */
class ChatCompletionRequest(
    val model: Model = Model.GPT_3_5_TURBO,
    val messages: List<Message>,
    val functions: List<Function>? = null,
    temperature: Double? = null,
    topP: Double? = null,
    n: Int? = null,
    stream: Boolean? = null,
    stop: List<String>? = null,
    presencePenalty: Double? = null,
    frequencyPenalty: Double? = null,
    logitBias: Map<String, Double>? = null,
    user: String? = null
) : CompletionRequestBase(
    temperature = temperature,
    topP = topP,
    n = n,
    stream = stream,
    stop = stop,
    presencePenalty = presencePenalty,
    frequencyPenalty = frequencyPenalty,
    logitBias = logitBias,
    user = user
) {
    enum class Model @JsonCreator constructor(
        @JsonValue val value: String
    ) {
        GPT_4("gpt-4"),
        GPT_4_32K("gpt-4-32k"),
        GPT_3_5_TURBO("gpt-3.5-turbo");
    }

    class Message(
        val role: Role = Role.USER,
        val content: String,
        val name: String? = null,
        val functionCall: FunctionCall? = null
    )

    class Function(
        val name: String,
        val description: String? = null,
        val parameters: Parameter? = null
    ) {
        class Parameter(
            val type: String = "object",
            val properties: Map<String, Property>? = null,
            val required: List<String>? = null
        ) {
            class Property(
                val type: String,
                val description: String? = null,
                @JsonProperty("enum") val enums: List<String>? = null
            )
        }
    }
}
