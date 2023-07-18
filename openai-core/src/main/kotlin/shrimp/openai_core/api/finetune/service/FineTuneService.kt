package shrimp.openai_core.api.finetune.service

import reactor.core.publisher.Mono
import shrimp.openai_core.api.finetune.request.FineTuneCreateRequest
import shrimp.openai_core.api.finetune.response.FineTuneEventListResponse
import shrimp.openai_core.api.finetune.response.FineTuneListResponse
import shrimp.openai_core.api.finetune.response.FineTuneResponse
import shrimp.openai_core.base.OpenAIClient
import shrimp.openai_core.base.OpenAIOption

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
    fun getFineTuneListAsync(
        option: OpenAIOption? = null
    ): Mono<FineTuneListResponse> {
        return openAIClient(option)
            .get()
            .uri("/fine-tunes")
            .retrieve()
            .bodyToMono(FineTuneListResponse::class.java)
    }

    /**
     * GET /fine-tunes. Blocking
     */
    fun getFineTuneList(
        option: OpenAIOption? = null
    ): FineTuneListResponse {
        return getFineTuneListAsync(option).block()!!
    }

    /**
     * GET /fine-tunes/{id}. Async
     */
    fun getFineTuneAsync(
        id: String,
        option: OpenAIOption? = null
    ): Mono<FineTuneResponse> {
        return openAIClient(option)
            .get()
            .uri("/fine-tunes/$id")
            .retrieve()
            .bodyToMono(FineTuneResponse::class.java)
    }

    /**
     * GET /fine-tunes/{id}. Blocking
     */
    fun getFineTune(
        id: String,
        option: OpenAIOption? = null
    ): FineTuneResponse {
        return getFineTuneAsync(id, option).block()!!
    }

    /**
     * GET /fine-tunes/{id}/events. Async
     */
    fun getFineTuneEventListAsync(
        id: String,
        option: OpenAIOption? = null
    ): Mono<FineTuneEventListResponse> {
        return openAIClient(option)
            .get()
            .uri("/fine-tunes/$id/events")
            .retrieve()
            .bodyToMono(FineTuneEventListResponse::class.java)
    }

    /**
     * GET /fine-tunes/{id}/events. Blocking
     */
    fun getFineTuneEventList(
        id: String,
        option: OpenAIOption? = null
    ): FineTuneEventListResponse {
        return getFineTuneEventListAsync(id, option).block()!!
    }

    /**
     * POST /fine-tunes. Async
     */
    fun postCreateFineTuneAsync(
        request: FineTuneCreateRequest,
        option: OpenAIOption? = null
    ): Mono<FineTuneResponse> {
        return openAIClient(option)
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
        request: FineTuneCreateRequest,
        option: OpenAIOption? = null
    ): FineTuneResponse {
        return postCreateFineTuneAsync(request, option).block()!!
    }

    /**
     * POST /fine-tunes/{id}/cancel. Async
     */
    fun postCancelFineTuneAsync(
        id: String,
        option: OpenAIOption? = null
    ): Mono<FineTuneResponse> {
        return openAIClient(option)
            .post()
            .uri("/fine-tunes/$id/cancel")
            .retrieve()
            .bodyToMono(FineTuneResponse::class.java)
    }

    /**
     * POST /fine-tunes/{id}/cancel. Blocking
     */
    fun postCancelFineTune(
        id: String,
        option: OpenAIOption? = null
    ): FineTuneResponse {
        return postCancelFineTuneAsync(id, option).block()!!
    }
}
