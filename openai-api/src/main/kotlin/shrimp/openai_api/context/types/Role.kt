package shrimp.openai_api.context.types

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class Role @JsonCreator constructor(
    @JsonValue val value: String
) {
    SYSTEM("system"),
    ASSISTANT("assistant"),
    USER("user");

    companion object {
        @JsonCreator
        fun fromValue(value: String): Role {
            return Role.values().first { it.value == value }
        }
    }
}
