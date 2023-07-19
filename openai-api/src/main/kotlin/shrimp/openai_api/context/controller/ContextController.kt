package shrimp.openai_api.context.controller

import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.web.bind.annotation.*
import shrimp.openai_api.context.dto.ContextCreateDTO
import shrimp.openai_api.context.entity.Context
import shrimp.openai_api.context.repository.ContextRepository
import shrimp.openai_api.context.repository.PromptRepository
import java.util.*

@RestController
@RequestMapping("/api/context")
class ContextController(
    private val promptRepository: PromptRepository,
    private val contextRepository: ContextRepository
) {
    private val logger = KotlinLogging.logger {}

    @GetMapping
    fun getContextList(): List<Context> {
        val contextList = contextRepository.findAllByOrderByUpdateAtDesc()
        return contextList;
    }

    @Transactional
    @PostMapping("/create")
    fun createContext(
        @RequestBody dto: ContextCreateDTO
    ): Context {
        val context = contextRepository.save(dto.convertEntity());

        val prePromptList = dto.prePromptList.map { it.convertEntity(context) }
        promptRepository.saveAll(prePromptList)

        return context;
    }
}