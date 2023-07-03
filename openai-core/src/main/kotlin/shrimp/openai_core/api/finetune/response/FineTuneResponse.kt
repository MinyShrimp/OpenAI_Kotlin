package shrimp.openai_core.api.finetune.response

import com.fasterxml.jackson.annotation.JsonProperty
import shrimp.openai_core.api.file.response.FileResponse

/**
 * Fine-Tuning API Response DTO
 * - GET /fine-tunes/{id}
 * - POST /fine-tunes
 * - POST /fine-tunes/{id}/cancel
 *
 * @author 김회민
 * @since 2023-03-27
 */
data class FineTuneResponse(
    val id: String? = null,
    val model: String? = null,
    @JsonProperty("object") val obj: String? = null,
    val organizationId: String? = null,
    val hyperparams: Hyperparams? = null,
    val trainingFiles: List<FileResponse>? = null,
    val validationFiles: List<FileResponse>? = null,
    val resultFiles: List<FileResponse>? = null,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val status: String? = null,
    val fineTunedModel: String? = null,
    val events: List<FineTuneEventListResponse.Event>? = null,
) {

    data class Hyperparams(
        val nEpochs: Long? = null,
        val batchSize: Long? = null,
        val promptLossWeight: Double? = null,
        val learningRateMultiplier: Double? = null,
        val classificationPositiveClass: String? = null,
        val computeClassificationMetrics: Boolean? = null,
    )
}
