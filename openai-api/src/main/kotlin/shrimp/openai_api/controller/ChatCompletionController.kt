package shrimp.openai_api.controller

import mu.KotlinLogging
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Flux
import shrimp.openai_core.api.completion.service.CompletionService
import java.util.*

@RestController
@RequestMapping("/api/chat")
class ChatCompletionController(
    val completionService: CompletionService,
) {
    private val logger = KotlinLogging.logger {}

    @PostMapping
    fun testChatCompletion(
        @RequestHeader("Authorization") auth: String,
        @RequestBody body: ChatCompletionDTO
    ): String {
        try {
            val resp = completionService.postChatCompletion(body.convert())
            return Optional.ofNullable(resp.choices?.first()?.message?.content).orElse("")
        } catch (e: WebClientResponseException.BadRequest) {
            logger.error { e.responseBodyAsString }
            throw e
        }
    }

    @PostMapping(
        "/stream",
        produces = ["text/event-stream"]
    )
    fun streamTestChatCompletion(
        @RequestHeader("Authorization") auth: String,
        @RequestBody body: ChatCompletionDTO
    ): Flux<String> {
        try {
            val resp = completionService.postChatCompletionStream(body.convert(true))
            return resp.map {
                it.choices?.first()?.delta?.content ?: ""
            }
        } catch (e: WebClientResponseException.BadRequest) {
            logger.error { e.responseBodyAsString }
            throw e
        }
    }
}
