package shrimp.openai_kotlin.api.finetune.service

import reactor.core.publisher.Mono
import shrimp.openai_kotlin.api.finetune.request.FineTuneCreateRequest
import shrimp.openai_kotlin.api.finetune.response.FineTuneEventListResponse
import shrimp.openai_kotlin.api.finetune.response.FineTuneListResponse
import shrimp.openai_kotlin.api.finetune.response.FineTuneResponse
import shrimp.openai_kotlin.base.OpenAIClient

/**
 * Fine-Tunings API Service
 *
 * @author 김회민
 * @see <a href="https://platform.openai.com/docs/guides/fine-tuning">Fine-tuning Guide</a>
 * @see <a href="https://platform.openai.com/docs/api-reference/fine-tunes">Fine-tunes API Document</a>
 * @since 2021-03-27
 */
class FineTuneService(
    private val openAIClient: OpenAIClient
) {

    /**
     * GET /fine-tunes. Async
     */
    fun getFineTuneListAsync(): Mono<FineTuneListResponse> {
        return openAIClient()
            .get()
            .uri("/fine-tunes")
            .retrieve()
            .bodyToMono(FineTuneListResponse::class.java)
    }

    /**
     * GET /fine-tunes. Blocking
     */
    fun getFineTuneList(): FineTuneListResponse {
        return getFineTuneListAsync().block()!!
    }

    /**
     * GET /fine-tunes/{id}. Async
     */
    fun getFineTuneAsync(
        id: String
    ): Mono<FineTuneResponse> {
        return openAIClient()
            .get()
            .uri("/fine-tunes/$id")
            .retrieve()
            .bodyToMono(FineTuneResponse::class.java)
    }

    /**
     * GET /fine-tunes/{id}. Blocking
     */
    fun getFineTune(id: String): FineTuneResponse {
        return getFineTuneAsync(id).block()!!
    }

    /**
     * GET /fine-tunes/{id}/events. Async
     */
    fun getFineTuneEventListAsync(
        id: String
    ): Mono<FineTuneEventListResponse> {
        return openAIClient()
            .get()
            .uri("/fine-tunes/$id/events")
            .retrieve()
            .bodyToMono(FineTuneEventListResponse::class.java)
    }

    /**
     * GET /fine-tunes/{id}/events. Blocking
     */
    fun getFineTuneEventList(
        id: String
    ): FineTuneEventListResponse {
        return getFineTuneEventListAsync(id).block()!!
    }

    /**
     * POST /fine-tunes. Async
     */
    fun postCreateFineTuneAsync(
        request: FineTuneCreateRequest
    ): Mono<FineTuneResponse> {
        return openAIClient()
            .post()
            .uri("/fine-tunes")
            .body(Mono.just(request), FineTuneCreateRequest::class.java)
            .retrieve()
            .bodyToMono(FineTuneResponse::class.java)
    }

    /**
     * POST /fine-tunes. Blocking
     */
    fun postCreateFineTune(
        request: FineTuneCreateRequest
    ): FineTuneResponse {
        return postCreateFineTuneAsync(request).block()!!
    }

    /**
     * POST /fine-tunes/{id}/cancel. Async
     */
    fun postCancelFineTuneAsync(
        id: String
    ): Mono<FineTuneResponse> {
        return openAIClient()
            .post()
            .uri("/fine-tunes/$id/cancel")
            .retrieve()
            .bodyToMono(FineTuneResponse::class.java)
    }

    /**
     * POST /fine-tunes/{id}/cancel. Blocking
     */
    fun postCancelFineTune(
        id: String
    ): FineTuneResponse {
        return postCancelFineTuneAsync(id).block()!!
    }
}
