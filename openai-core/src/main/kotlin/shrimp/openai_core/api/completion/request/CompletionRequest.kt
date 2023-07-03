package shrimp.openai_core.api.completion.request

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
class CompletionRequest(
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

    sealed class Model @JsonCreator constructor(
        @JsonValue val value: String
    ) {
        object DAVINCI_003 : Model("text-davinci-003")
        object DAVINCI_002 : Model("text-davinci-002")
        object CURIE_001 : Model("text-curie-001")
        object BABBAGE_001 : Model("text-babbage-001")
        object ADA_001 : Model("text-ada-001")

        object DAVINCI : Model("davinci")
        object CURIE : Model("curie")
        object BABBAGE : Model("babbage")
        object ADA : Model("ada")

        class CUSTOM(private val name: String) : Model(name)
    }
}
