package shrimp.openai_kotlin.api.edit.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import shrimp.openai_kotlin.api.edit.request.EditRequest
import shrimp.openai_kotlin.api.edit.response.EditResponse
import shrimp.openai_kotlin.base.OpenAIClient

/**
 * Edit API Service
 *
 * @author 김회민
 * @see <a href="https://platform.openai.com/docs/api-reference/edits/create">Edits API Document</a>
 * @since 2023-03-26
 */
@Service
class EditService(
    private val openAIClient: OpenAIClient
) {

    /**
     * POST /edits. Async.
     */
    fun postCreateEditAsync(
        request: EditRequest
    ): Mono<EditResponse> {
        return openAIClient()
            .post()
            .uri("/edits")
            .body(Mono.just(request), EditRequest::class.java)
            .retrieve()
            .bodyToMono(EditResponse::class.java)
    }

    /**
     * POST /edits. Blocking.
     */
    fun postCreateEdit(
        request: EditRequest
    ): EditResponse {
        return postCreateEditAsync(request).block()!!
    }
}
