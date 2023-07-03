package shrimp.openai_core.api.finetune.request

import com.fasterxml.jackson.annotation.JsonValue

/**
 * Fine-Tuning API Request DTO
 * - Post /fine-tunes
 *
 * @author 김회민
 * @since 2023-03-27
 */
class FineTuneCreateRequest(
    val model: Model = Model.DAVINCI,

    val trainingFile: String,

    val validationFile: String? = null,

    val nEpochs: Int? = null,

    val batchSize: Int? = null,

    val learningRateMultiplier: Double? = null,

    val promptLossWeight: Double? = null,

    val computeClassificationMetrics: Boolean? = null,

    val classificationNClasses: Int? = null,

    val classificationPositiveClass: String? = null,

    val classificationBetas: List<Double>? = null,

    val suffix: String? = null
) {

    /**
     * @author 김회민
     * @see <a href="platform.openai.com/docs/models/gpt-3">Model List</a>
     * @since 2023-03-27
     */
    enum class Model(
        @JsonValue val value: String
    ) {
        DAVINCI("davinci"),
        CURIE("curie"),
        BABBAGE("babbage"),
        ADA("ada");
    }
}
