package shrimp.openai_core.api.edit.response

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Edit API Response DTO
 *
 * @author 김회민
 * @since 2021-03-26
 */
class EditResponse(
    @JsonProperty("object") val obj: String? = null,
    val created: Long? = null,
    val choices: List<Choice>? = null,
    val usage: Usage? = null,
) {

    class Choice(
        val text: String? = null,
        val index: Int? = null,
    )

    class Usage(
        val promptTokens: Long? = null,
        val completionTokens: Long? = null,
        val totalTokens: Long? = null,
    )
}
