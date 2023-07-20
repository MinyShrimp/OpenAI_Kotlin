package shrimp.openai_api.context.controller

import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.web.bind.annotation.*
import shrimp.openai_api.context.dto.request.CreateContextRequest
import shrimp.openai_api.context.dto.response.CreateContextResponse
import shrimp.openai_api.context.dto.response.GetContextResponse
import shrimp.openai_api.context.dto.response.GetHistoryResponse
import shrimp.openai_api.context.dto.response.GetPromptResponse
import shrimp.openai_api.context.repository.ContextRepository
import shrimp.openai_api.context.repository.HistoryRepository
import shrimp.openai_api.context.repository.PromptRepository
import java.util.*

@RestController
@RequestMapping("/api/context")
class ContextController(
    private val promptRepository: PromptRepository,
    private val contextRepository: ContextRepository,
    private val historyRepository: HistoryRepository
) {
    private val logger = KotlinLogging.logger {}

    @GetMapping
    fun getContextList(): List<GetContextResponse> {
        val contextList = contextRepository.findAllByOrderByUpdateAtDesc()
        return contextList.map { GetContextResponse.of(it) }
    }

    @GetMapping("/{id}/prompt")
    fun getPromptList(
        @PathVariable("id") contextId: UUID
    ): List<GetPromptResponse> {
        val promptList = promptRepository.findByContextIdOrderByOrderAsc(contextId)
        return promptList.map { GetPromptResponse.of(it) }
    }

    @GetMapping("/{id}/history")
    fun getHistoryList(
        @PathVariable("id") contextId: UUID
    ): List<GetHistoryResponse> {
        val historyList = historyRepository.findByContextIdOrderByCreateAt(contextId)
        return historyList.map { GetHistoryResponse.of(it) }
    }

    @Transactional
    @PostMapping("/create")
    fun createContext(
        @RequestBody dto: CreateContextRequest
    ): CreateContextResponse {
        val context = dto.convertEntity()
        val prePromptList = dto.prePromptList
            .mapIndexed { index, prePromptDTO -> prePromptDTO.convertEntity(index, context) }
        context.prePromptList = prePromptList

        val savedContext = contextRepository.save(context)
        return CreateContextResponse.of(savedContext)
    }
}