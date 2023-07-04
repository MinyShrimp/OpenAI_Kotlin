package shrimp.openai_core.api.edit.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

/**
 * Edit API Request DTO
 *
 * @author 김회민
 * @since 2021-03-26
 */
class EditRequest(
    val model: Model,
    val input: String,
    val instruction: String,
    val n: Int? = null,
    val temperature: Double? = null,
    val topP: Double? = null
) {

    enum class Model @JsonCreator constructor(
        @JsonValue val value: String
    ) {
        TEXT("text-davinci-edit-001"),
        CODE("code-davinci-edit-001");
    }
}
