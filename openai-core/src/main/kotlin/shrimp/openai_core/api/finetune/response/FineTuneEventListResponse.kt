package shrimp.openai_core.api.finetune.response

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Fine-Tuning API - Event List Response DTO
 * - GET /fine-tunes/{id}/events
 *
 * @author 김회민
 * @since 2023-03-27
 */
data class FineTuneEventListResponse(
    @JsonProperty("object") val obj: String? = null,
    val data: List<Event>? = null
) {

    data class Event(
        @JsonProperty("object") val obj: String? = null,
        val level: String? = null,
        val message: String? = null,
        val createdAt: Long? = null
    )
}
