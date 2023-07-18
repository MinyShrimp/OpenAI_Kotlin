package shrimp.openai_core.api.finetune.response

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Fine-Tuning API - List Response DTO
 * - GET /fine-tunes
 *
 * @author 김회민
 * @since 2023-03-27
 */
data class FineTuneListResponse(
    val data: List<FineTuneResponse>? = null,
    @JsonProperty("object") val obj: String? = null,
)
