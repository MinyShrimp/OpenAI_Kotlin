package shrimp.openai_core.api.completion.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import shrimp.openai_core.api.completion.request.ChatCompletionRequest
import shrimp.openai_core.api.completion.request.CompletionRequest
import shrimp.openai_core.api.completion.response.ChatCompletionResponse
import shrimp.openai_core.api.completion.response.CompletionResponse
import shrimp.openai_core.base.OpenAIClient
import shrimp.openai_core.base.OpenAIOption

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
        request: CompletionRequest,
        option: OpenAIOption?
    ): WebClient.ResponseSpec {
        return openAIClient(option)
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
        request: ChatCompletionRequest,
        option: OpenAIOption?
    ): WebClient.ResponseSpec {
        return openAIClient(option)
            .post()
            .uri("/chat/completions")
            .body(Mono.just(request), ChatCompletionRequest::class.java)
            .retrieve()
    }

    /**
     * Streaming Response
     * - 공통 부분 추출
     */
    private inline fun <reified T> fluxResponse(
        responseSpec: WebClient.ResponseSpec
    ): Flux<T> {
        return responseSpec
            .bodyToFlux(String::class.java)
            .filter { it != "[DONE]" }
            .map { objectMapper.readValue<T>(it) }
    }

    /**
     * POST /completions. Async
     */
    fun postCompletionAsync(
        request: CompletionRequest,
        option: OpenAIOption? = null
    ): Mono<CompletionResponse> {
        return postCompletionResponseSpec(request, option)
            .bodyToMono(CompletionResponse::class.java)
    }

    /**
     * POST /completions. Stream
     */
    fun postCompletionStream(
        request: CompletionRequest,
        option: OpenAIOption? = null
    ): Flux<CompletionResponse> {
        return fluxResponse<CompletionResponse>(
            postCompletionResponseSpec(request, option)
        )
    }

    /**
     * POST /completions. Block
     */
    fun postCompletion(
        request: CompletionRequest,
        option: OpenAIOption? = null
    ): CompletionResponse {
        return postCompletionAsync(request, option).block()!!
    }

    /**
     * POST /chat/completions. Async
     */
    fun postChatCompletionAsync(
        request: ChatCompletionRequest,
        option: OpenAIOption? = null
    ): Mono<ChatCompletionResponse> {
        return postChatCompletionResponseSpec(request, option)
            .bodyToMono(ChatCompletionResponse::class.java)
    }

    /**
     * POST /chat/completions. Stream
     */
    fun postChatCompletionStream(
        request: ChatCompletionRequest,
        option: OpenAIOption? = null
    ): Flux<ChatCompletionResponse> {
        return fluxResponse<ChatCompletionResponse>(
            postChatCompletionResponseSpec(request, option)
        )
    }

    /**
     * POST /chat/completions. Block
     */
    fun postChatCompletion(
        request: ChatCompletionRequest,
        option: OpenAIOption? = null
    ): ChatCompletionResponse {
        return postChatCompletionAsync(request, option).block()!!
    }
}
