package shrimp.openai_api.context.controller

import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.web.bind.annotation.*
import shrimp.openai_api.context.dto.request.CreateContextRequest
import shrimp.openai_api.context.dto.response.*
import shrimp.openai_api.context.repository.ContextRepository
import shrimp.openai_api.context.repository.PromptRepository
import java.time.LocalDateTime
import java.util.*

@RestController
@RequestMapping("/api/context")
class ContextController(
    private val promptRepository: PromptRepository,
    private val contextRepository: ContextRepository,
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

    @Transactional
    @PostMapping
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

    @Transactional
    @PutMapping
    fun updateContext(
        @RequestBody dto: CreateContextRequest
    ): CreateContextResponse {
        val isExists = contextRepository.existsById(dto.id)
        if (!isExists) {
            throw IllegalArgumentException("Context not found")
        }
        
        val context = contextRepository.findById(dto.id).get()
        context.title = dto.title
        context.model = dto.model
        context.description = dto.description

        promptRepository.deleteAllByContextId(dto.id)
        context.prePromptList = dto.prePromptList
            .mapIndexed { index, prePromptDTO -> prePromptDTO.convertEntity(index, context) }

        val savedContext = contextRepository.save(context)
        return CreateContextResponse.of(savedContext)
    }

    @Transactional
    @DeleteMapping("/{id}")
    fun deleteContext(
        @PathVariable("id") contextId: UUID,
    ): UUID {
        val isExists = contextRepository.existsById(contextId)
        if (!isExists) {
            throw IllegalArgumentException("Context not found")
        }

        val context = contextRepository.findById(contextId).get()
        context.deleteAt = LocalDateTime.now()
        contextRepository.save(context)
        return contextId
    }
}