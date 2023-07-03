package shrimp.openai_core.api.file.response

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * File Delete Response DTO
 * - DELETE /files/{id}
 *
 * @author 김회민
 * @since 2023-03-26
 */
class FileDeleteResponse(
    val id: String? = null,
    @JsonProperty("object") val obj: String? = null,
    val deleted: Boolean? = null
)
