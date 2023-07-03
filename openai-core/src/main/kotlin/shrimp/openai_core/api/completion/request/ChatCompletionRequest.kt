package shrimp.openai_core.api.completion.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

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
        val name: String? = null
    ) {
        enum class Role @JsonCreator constructor(
            @JsonValue val value: String
        ) {
            SYSTEM("system"),
            USER("user"),
            ASSISTANT("assistant"),
            FUNCTION("function");
        }
    }
}
