package shrimp.openai_api.chat_completion.controller

import mu.KotlinLogging
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Flux
import shrimp.openai_api.chat_completion.dto.ChatCompletionDTO
import shrimp.openai_core.api.completion.service.CompletionService
import shrimp.openai_core.base.OpenAIOption
import java.util.*

@RestController
@RequestMapping("/api/chat")
class ChatCompletionController(
    val completionService: CompletionService,
) {
    private val logger = KotlinLogging.logger {}

    @PostMapping(
        "/stream",
        produces = ["text/event-stream"]
    )
    fun streamTestChatCompletion(
        @RequestHeader("Authorization") auth: String,
        @RequestBody body: ChatCompletionDTO
    ): Flux<String> {
        try {
            val resp = completionService.postChatCompletionStream(
                body.convert(true),
                OpenAIOption(auth.replace("Bearer ", ""))
            )
            return resp.map {
                it.choices?.first()?.delta?.content ?: ""
            }
        } catch (e: WebClientResponseException.BadRequest) {
            logger.error { e.responseBodyAsString }
            throw e
        }
    }
}
