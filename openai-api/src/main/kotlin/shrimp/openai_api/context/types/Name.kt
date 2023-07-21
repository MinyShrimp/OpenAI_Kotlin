package shrimp.openai_api.context.types

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class Name @JsonCreator constructor(
    @JsonValue val value: String
) {
    SYSTEM("system"),
    AI("ai"),
    USER("user");

    companion object {
        @JsonCreator
        fun fromValue(value: String): Name {
            return Name.values().first { it.value == value }
        }
    }
}
