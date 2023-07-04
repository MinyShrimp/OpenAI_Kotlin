package shrimp.openai_api.controller

import mu.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClientResponseException
import shrimp.openai_core.api.completion.entity.Role
import shrimp.openai_core.api.completion.request.ChatCompletionRequest
import shrimp.openai_core.api.completion.service.CompletionService
import java.util.*

@RestController
@RequestMapping("/api/chat")
class ChatCompletionController(
    val completionService: CompletionService,
) {
    private val logger = KotlinLogging.logger {}

    @GetMapping
    fun testChatCompletion(): String {
        val req = ChatCompletionRequest(
            model = ChatCompletionRequest.Model.GPT_3_5_TURBO,
            messages = listOf(
                ChatCompletionRequest.Message(
                    role = Role.USER,
                    content = "Hi!"
                )
            )
        )

        try {
            val resp = completionService.postChatCompletion(req)
            return Optional.ofNullable(resp.choices?.first()?.message?.content).orElse("")
        } catch (e: WebClientResponseException.BadRequest) {
            logger.error { e.responseBodyAsString }
            throw e
        }
    }
}
