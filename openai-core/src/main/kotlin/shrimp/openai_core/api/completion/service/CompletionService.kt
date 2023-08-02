package shrimp.openai_core.api.completion.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import shrimp.openai_core.api.completion.request.ChatCompletionRequest
import shrimp.openai_core.api.completion.request.CompletionRequest
import shrimp.openai_core.api.completion.response.ChatCompletionResponse
import shrimp.openai_core.api.completion.response.CompletionResponse
import shrimp.openai_core.base.OpenAIClient
import shrimp.openai_core.base.OpenAIOption
import shrimp.openai_core.utility.body

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
    private val completionUri: String = "/completions"
    private val chatCompletionUri: String = "/chat/completions"

    private inline fun <reified T> Flux<String>.convert(): Flux<T> {
        return this.filter { it != "[DONE]" }.map { objectMapper.readValue<T>(it) }
    }

    /**
     * POST /completions. Async
     */
    fun postCompletionAsync(
        request: CompletionRequest,
        option: OpenAIOption? = null
    ): Mono<CompletionResponse> {
        return openAIClient(option)
            .post().uri(completionUri)
            .body(Mono.just(request)).retrieve()
            .bodyToMono(CompletionResponse::class.java)
    }

    /**
     * POST /completions. Stream
     */
    fun postCompletionStream(
        request: CompletionRequest,
        option: OpenAIOption? = null
    ): Flux<CompletionResponse> {
        return openAIClient(option)
            .post().uri(completionUri)
            .body(Mono.just(request)).retrieve()
            .bodyToFlux(String::class.java).convert()
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
        return openAIClient(option)
            .post().uri(chatCompletionUri)
            .body(Mono.just(request)).retrieve()
            .bodyToMono(ChatCompletionResponse::class.java)
    }

    /**
     * POST /chat/completions. Stream
     */
    fun postChatCompletionStream(
        request: ChatCompletionRequest,
        option: OpenAIOption? = null
    ): Flux<ChatCompletionResponse> {
        return openAIClient(option)
            .post().uri(chatCompletionUri)
            .body(Mono.just(request)).retrieve()
            .bodyToFlux(String::class.java).convert()
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
