package shrimp.openai_core.api.completion.entity

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class Logprobs @JsonCreator constructor(
    @JsonProperty("tokens") val tokens: List<String>? = null,
    @JsonProperty("token_logprobs") val tokenLogprobs: List<Double>? = null,
    @JsonProperty("top_logprobs") val topLogprobs: List<Map<String, Double>>? = null,
    @JsonProperty("text_offset") val textOffset: List<Int>? = null,
)
