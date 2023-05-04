package shrimp.openai_kotlin.api.moderation.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import shrimp.openai_kotlin.api.moderation.request.ModerationRequest
import shrimp.openai_kotlin.api.moderation.response.ModerationResponse
import shrimp.openai_kotlin.base.OpenAIClient

/**
 * Moderation API Service
 *
 * @author 김회민
 * @see <a href="https://platform.openai.com/docs/guides/moderation">Moderation Guide</a>
 * @see <a href="https://openai.com/policies/usage-policies">Usage Policies</a>
 * @see <a href="https://openai.com/policies/api-data-usage-policies">API Data Usage Policies</a>
 * @see <a href="https://platform.openai.com/docs/api-reference/moderations">Moderation API Document</a>
 * @since 2023-03-26
 */
@Service
class ModerationService(
    private val client: OpenAIClient
) {

    /**
     * POST /moderations. Async.
     */
    fun postCreateModerationAsync(
        request: ModerationRequest
    ): Mono<ModerationResponse> {
        return client()
            .post()
            .uri("/moderations")
            .body(Mono.just(request), ModerationRequest::class.java)
            .retrieve()
            .bodyToMono(ModerationResponse::class.java)
    }

    /**
     * POST /moderations. Blocking.
     */
    fun postCreateModeration(
        request: ModerationRequest
    ): ModerationResponse {
        return postCreateModerationAsync(request).block()!!
    }
}
