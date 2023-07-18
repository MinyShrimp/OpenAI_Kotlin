package shrimp.openai_core.api.moderation.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import shrimp.openai_core.api.moderation.request.ModerationRequest
import shrimp.openai_core.api.moderation.response.ModerationResponse
import shrimp.openai_core.base.OpenAIClient
import shrimp.openai_core.base.OpenAIOption

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
        request: ModerationRequest,
        option: OpenAIOption? = null
    ): Mono<ModerationResponse> {
        return client(option)
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
        request: ModerationRequest,
        option: OpenAIOption? = null
    ): ModerationResponse {
        return postCreateModerationAsync(request, option).block()!!
    }
}
