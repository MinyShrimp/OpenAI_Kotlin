package shrimp.openai_core.api.model.response

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Model API Response DTO
 *
 * @author 김회민
 * @see <a href="https://platform.openai.com/docs/api-reference/models">Models API Document</a>
 * @since 2023-03-26
 */
data class ModelListResponse(
    @JsonProperty("object") val obj: String? = null,
    val data: List<ModelResponse>? = null
)
