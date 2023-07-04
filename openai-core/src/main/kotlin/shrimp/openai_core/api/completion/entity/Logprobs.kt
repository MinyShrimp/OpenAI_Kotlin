package shrimp.openai_core.api.completion.entity

data class Logprobs(
    val tokens: List<String>? = null,
    val tokenLogprobs: List<Double>? = null,
    val topLogprobs: List<Map<String, Double>>? = null,
    val textOffset: List<Int>? = null,
)
