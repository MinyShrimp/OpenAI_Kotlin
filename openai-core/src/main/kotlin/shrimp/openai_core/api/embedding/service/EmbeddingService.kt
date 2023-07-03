package shrimp.openai_core.api.embedding.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import shrimp.openai_core.api.embedding.request.EmbeddingRequest
import shrimp.openai_core.api.embedding.response.EmbeddingResponse
import shrimp.openai_core.base.OpenAIClient

/**
 * Embeddings API Service
 *
 * @author 김회민
 * @see <a href="https://platform.openai.com/docs/guides/embeddings/what-are-embeddings">Guide</a>
 * @see <a href="https://platform.openai.com/docs/api-reference/embeddings">API Document</a>
 * @since 2023-03-26
 */
@Service
class EmbeddingService(
    private val openAIClient: OpenAIClient
) {

    /**
     * POST /embeddings. Async.
     */
    fun postCreateEmbeddingsAsync(
        request: EmbeddingRequest
    ): Mono<EmbeddingResponse> {
        return openAIClient()
            .post()
            .uri("/embeddings")
            .body(Mono.just(request), EmbeddingRequest::class.java)
            .retrieve()
            .bodyToMono(EmbeddingResponse::class.java)
    }

    /**
     * POST /embeddings. Block.
     */
    fun postCreateEmbeddings(
        request: EmbeddingRequest
    ): EmbeddingResponse {
        return postCreateEmbeddingsAsync(request).block()!!
    }
}
