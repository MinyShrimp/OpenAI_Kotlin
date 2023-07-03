package shrimp.openai_core.api.completion.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import shrimp.openai_core.api.completion.request.ChatCompletionRequest
import shrimp.openai_core.api.completion.request.CompletionRequest
import shrimp.openai_core.api.completion.response.CompletionResponse
import shrimp.openai_core.base.OpenAIClient

/**
 * Completion API Service
 *
 * @author 김회민
 * @see <a href="https://platform.openai.com/docs/guides/completion">Text Completion Guide</a>
 * @see <a href="https://platform.openai.com/docs/guides/code">Code Completion Guide</a>
 * @see <a href="https://platform.openai.com/docs/guides/chat">Chat Completion Guide</a>
 * @see <a href="https://platform.openai.com/docs/api-reference/completions">Completion API Document</a>
 * @see <a href="https://platform.openai.com/docs/api-reference/chat">Chat API Document</a>
 * @since 2023-03-26
 */
@Service
class CompletionService(
    private val openAIClient: OpenAIClient,
    private val objectMapper: ObjectMapper
) {

    /**
     * POST /completions
     * - 공통 부분 추출
     */
    private fun postCompletionResponseSpec(
        request: CompletionRequest
    ): WebClient.ResponseSpec {
        return openAIClient()
            .post()
            .uri("/completions")
            .body(Mono.just(request), CompletionRequest::class.java)
            .retrieve()
    }

    /**
     * POST /chat/completions
     * - 공통 부분 추출
     */
    private fun postChatCompletionResponseSpec(
        request: ChatCompletionRequest
    ): WebClient.ResponseSpec {
        return openAIClient()
            .post()
            .uri("/chat/completions")
            .body(Mono.just(request), ChatCompletionRequest::class.java)
            .retrieve()
    }

    /**
     * Streaming Response
     * - 공통 부분 추출
     */
    private fun fluxResponse(
        responseSpec: WebClient.ResponseSpec
    ): Flux<CompletionResponse> {
        return responseSpec
            .bodyToFlux(String::class.java)
            .filter { it != "[DONE]" }
            .map {
                objectMapper.readValue(it, CompletionResponse::class.java)
            }
    }

    /**
     * POST /completions. Async
     */
    fun postCompletionAsync(
        request: CompletionRequest
    ): Mono<CompletionResponse> {
        return postCompletionResponseSpec(request)
            .bodyToMono(CompletionResponse::class.java)
    }

    /**
     * POST /completions. Stream
     */
    fun postCompletionStream(
        request: CompletionRequest
    ): Flux<CompletionResponse> {
        return fluxResponse(
            postCompletionResponseSpec(request)
        )
    }

    /**
     * POST /completions. Block
     */
    fun postCompletion(
        request: CompletionRequest
    ): CompletionResponse {
        return postCompletionAsync(request).block()!!
    }

    /**
     * POST /chat/completions. Async
     */
    fun postChatCompletionAsync(
        request: ChatCompletionRequest
    ): Mono<CompletionResponse> {
        return postChatCompletionResponseSpec(request)
            .bodyToMono(CompletionResponse::class.java)
    }

    /**
     * POST /chat/completions. Stream
     */
    fun postChatCompletionStream(
        request: ChatCompletionRequest
    ): Flux<CompletionResponse> {
        return fluxResponse(
            postChatCompletionResponseSpec(request)
        )
    }

    /**
     * POST /chat/completions. Block
     */
    fun postChatCompletion(
        request: ChatCompletionRequest
    ): CompletionResponse {
        return postChatCompletionAsync(request).block()!!
    }
}
