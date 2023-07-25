package shrimp.openai_api.context.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import shrimp.openai_api.context.dto.response.GetPromptResponse
import shrimp.openai_api.context.service.PromptService
import java.util.*

@RestController
@RequestMapping("/api/context")
class PromptController(
    private val promptService: PromptService,
) {
    @GetMapping("/{id}/prompt")
    fun getPromptList(
        @PathVariable("id") contextId: UUID
    ): List<GetPromptResponse> {
        val promptList = this.promptService.getPromptList(contextId)
        return promptList.map { GetPromptResponse.of(it) }
    }
}
