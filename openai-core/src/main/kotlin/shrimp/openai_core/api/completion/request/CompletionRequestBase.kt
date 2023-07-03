package shrimp.openai_core.api.completion.request

/**
 * Completions API Request DTO 부모
 * - DefaultCompletionRequest
 * - ChatCompletionRequest
 *
 * @author 김회민
 * @see CompletionRequest
 * @see ChatCompletionRequest
 * @see <a href="https://platform.openai.com/docs/api-reference/completions">Completion API Document</a>
 * @since 2023-03-26
 */
open class CompletionRequestBase(
    val temperature: Double? = null,
    val topP: Double? = null,
    val n: Int? = null,
    val stream: Boolean? = null,
    val stop: List<String>? = null,
    val presencePenalty: Double? = null,
    val frequencyPenalty: Double? = null,
    val logitBias: Map<String, Double>? = null,
    val user: String? = null
)
