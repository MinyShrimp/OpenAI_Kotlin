package shrimp.openai_core.api.completion.entity

data class Usage(
    val completionTokens: Long? = null,
    val promptTokens: Long? = null,
    val totalTokens: Long? = null,
)
