package shrimp.openai_core.api.file.response

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * File Response DTO
 * - GET /files/{id}
 * - POST /files
 *
 * @author 김회민
 * @since 2023-03-26
 */
data class FileResponse(
    val id: String? = null,
    @JsonProperty("object") val obj: String? = null,
    val filename: String? = null,
    val purpose: String? = null,
    val bytes: Long? = null,
    val createdAt: Long? = null,
    val status: String? = null,
    val statusDetails: Any? = null,
)
