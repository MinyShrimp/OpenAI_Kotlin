package shrimp.openai_core.api.moderation.request

import com.fasterxml.jackson.annotation.JsonValue

/**
 * Moderation API Request DTO
 *
 * @author 김회민
 * @see <a href="https://platform.openai.com/docs/api-reference/moderations">Moderation API Document</a>
 * @since 2023-03-26
 */
class ModerationRequest(
    val model: Model = Model.LATEST,
    val input: List<String>
) {

    /**
     * @author 김회민
     * @see <a href="https://platform.openai.com/docs/models/gpt-3">Model List</a>
     * @since 2023-03-26
     */
    enum class Model(
        @JsonValue val value: String
    ) {
        LATEST("text-moderation-latest"),
        STABLE("text-moderation-stable");
    }
}
