package shrimp.openai_core.api.embedding.response

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Embeddings 응답 객체
 *
 * @author 김회민
 * @see <a href="https://platform.openai.com/docs/api-reference/embeddings">API Document</a>
 * @since 2023-03-26
 */
class EmbeddingResponse(
    val model: String? = null,
    @JsonProperty("object") val obj: String? = null,
    val usage: Usage? = null,
    val data: List<Data>? = null
) {
    class Usage(
        val promptTokens: Long? = null,
        val totalTokens: Long? = null
    )

    class Data(
        val index: Long? = null,
        @JsonProperty("object") val obj: String? = null,
        val embedding: List<Double>? = null
    )
}
