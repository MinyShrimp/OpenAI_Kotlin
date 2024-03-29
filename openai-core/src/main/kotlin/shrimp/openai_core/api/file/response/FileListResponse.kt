package shrimp.openai_core.api.file.response

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * File List Response DTO
 * - GET /files
 *
 * @author 김회민
 * @since 2023-03-26
 */
data class FileListResponse(
    val data: List<FileResponse>? = null,
    @JsonProperty("object") val obj: String? = null
)
