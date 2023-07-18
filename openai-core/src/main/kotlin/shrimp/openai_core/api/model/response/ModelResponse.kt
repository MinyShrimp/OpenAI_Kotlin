package shrimp.openai_core.api.model.response

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Model API Response DTO
 *
 * @author 김회민
 * @see <a href="https://platform.openai.com/docs/api-reference/models">Models API Document</a>
 * @since 2023-03-26
 */
data class ModelResponse(
    val id: String? = null,
    @JsonProperty("object") val obj: String? = null,
    val created: Long? = null,
    val ownedBy: String? = null,
    val root: String? = null,
    val parent: String? = null,
    val permission: List<Permission>? = null
) {
    data class Permission(
        val id: String? = null,
        val isBlocking: Boolean? = null,
        @JsonProperty("object") val obj: String? = null,
        val organization: String? = null,
        val allowCreateEngine: Boolean? = null,
        val allowFineTuning: Boolean? = null,
        val allowLogprobs: Boolean? = null,
        val allowSampling: Boolean? = null,
        val allowSearchIndices: Boolean? = null,
        val allowView: Boolean? = null,
        val created: Long? = null,
        val group: Any? = null
    )
}
