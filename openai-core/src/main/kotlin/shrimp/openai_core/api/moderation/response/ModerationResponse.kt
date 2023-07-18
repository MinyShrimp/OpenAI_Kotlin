package shrimp.openai_core.api.moderation.response

import com.fasterxml.jackson.annotation.JsonProperty

data class ModerationResponse(
    val id: String? = null,
    val model: String? = null,
    val results: List<Result>? = null,
) {
    data class Result(
        val categories: Categories? = null,
        val categoryScores: CategoryScores? = null,
        val flagged: Boolean? = null
    ) {
        data class Categories(
            val hate: Boolean? = null,
            @JsonProperty("hate/threatening") val hateThreatening: Boolean? = null,
            @JsonProperty("self-harm") val selfHarm: Boolean? = null,
            val sexual: Boolean? = null,
            @JsonProperty("sexual/minors") val sexualMinors: Boolean? = null,
            val violence: Boolean? = null,
            @JsonProperty("violence/graphic") val violenceGraphic: Boolean? = null
        )

        data class CategoryScores(
            val hate: Double? = null,
            @JsonProperty("hate/threatening") val hateThreatening: Double? = null,
            @JsonProperty("self-harm") val selfHarm: Double? = null,
            val sexual: Double? = null,
            @JsonProperty("sexual/minors") val sexualMinors: Double? = null,
            val violence: Double? = null,
            @JsonProperty("violence/graphic") val violenceGraphic: Double? = null
        )
    }
}
