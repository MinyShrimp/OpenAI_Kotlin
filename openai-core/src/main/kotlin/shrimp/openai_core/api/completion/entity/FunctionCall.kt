package shrimp.openai_core.api.completion.entity

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

data class FunctionCall private constructor(
    @JsonProperty("name") val name: String,
    @JsonProperty("arguments") val arguments: String? = null,
    @JsonIgnore val argumentsMap: Map<String, String>? = null
) {
    constructor(
        name: String,
        argumentsMap: Map<String, String>? = null
    ) : this(
        name,
        argumentsMap?.let { ObjectMapper().writeValueAsString(it) },
        argumentsMap
    )

    @JsonCreator
    constructor(
        @JsonProperty("name") name: String,
        @JsonProperty("arguments") arguments: String? = null
    ) : this(
        name,
        arguments,
        arguments?.let { ObjectMapper().readValue<HashMap<String, String>>(it) }
    )
}
