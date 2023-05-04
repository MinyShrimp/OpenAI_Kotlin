package shrimp.openai_kotlin.api.completion.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

/**
 * Completions API Request DTO
 * - POST /v1/completions
 *
 * @author 김회민
 * @see <a href="https://platform.openai.com/docs/api-reference/completions">Completions API Document</a>
 * @since 2023-03-26
 */
class DefaultCompletionRequest(
    val model: Model = Model.DAVINCI_003,
    val prompt: List<String>,
    val suffix: String? = null,
    val maxTokens: Int? = null,
    val logprobs: Int? = null,
    val echo: Boolean? = null,
    val bestOf: Int? = null,
    temperature: Double? = null,
    topP: Double? = null,
    n: Int? = null,
    stream: Boolean? = null,
    stop: List<String>? = null,
    presencePenalty: Double? = null,
    frequencyPenalty: Double? = null,
    logitBias: Map<String, Double>? = null,
    user: String? = null
) : CompletionRequest(
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
        DAVINCI_003("text-davinci-003"),
        DAVINCI_002("text-davinci-002"),
        CURIE_001("text-curie-001"),
        BABBAGE_001("text-babbage-001"),
        ADA_001("text-ada-001"),

        DAVINCI("davinci"),
        CURIE("curie"),
        BABBAGE("babbage"),
        ADA("ada");
    }
}
