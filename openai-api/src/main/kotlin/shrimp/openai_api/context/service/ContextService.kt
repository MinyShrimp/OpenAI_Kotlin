package shrimp.openai_api.context.service

import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.stereotype.Service
import shrimp.openai_api.context.dto.request.CreateContextRequest
import shrimp.openai_api.context.dto.request.UpdateContextRequest
import shrimp.openai_api.context.entity.Context
import shrimp.openai_api.context.repository.ContextRepository
import shrimp.openai_api.context.repository.PromptRepository
import shrimp.openai_api.security.service.AccountSessionService
import java.time.LocalDateTime
import java.util.*

@Service
class ContextService(
    private val promptRepository: PromptRepository,
    private val contextRepository: ContextRepository,
    private val accountSessionService: AccountSessionService
) {
    private val logger = KotlinLogging.logger {}

    fun getContext(
        token: String,
        contextId: UUID
    ): Context {
        val context = this.contextRepository.findById(contextId)
            .orElseThrow { IllegalArgumentException("Context not found") }

        val account = this.accountSessionService.getAccountByToken(token)
        if (account.id != context.account?.id) {
            throw IllegalArgumentException("Context not found")
        }

        return context
    }

    fun getContextList(
        token: String
    ): List<Context> {
        val account = this.accountSessionService.getAccountByToken(token)
        return contextRepository.findAllByAccountOrderByUpdateAtDesc(account)
    }

    @Transactional
    fun createContext(
        token: String,
        dto: CreateContextRequest
    ): Context {
        val account = this.accountSessionService.getAccountByToken(token)
        val context = dto.convertEntity(account)

        val prePromptList = dto.prePromptList
            .mapIndexed { index, prePromptDTO -> prePromptDTO.convertEntity(index, context) }
        context.prePromptList = prePromptList

        return this.contextRepository.save(context)
    }

    @Transactional
    fun updateContext(
        token: String,
        dto: UpdateContextRequest
    ): Context {
        val context = this.getContext(token, dto.id)
        context.title = dto.title
        context.model = dto.model
        context.description = dto.description

        promptRepository.deleteAllByContextId(dto.id)
        context.prePromptList = dto.prePromptList
            .mapIndexed { index, prePromptDTO -> prePromptDTO.convertEntity(index, context) }

        return contextRepository.save(context)
    }

    @Transactional
    fun deleteContext(
        token: String,
        contextId: UUID
    ): Context {
        val context = this.getContext(token, contextId)
        context.deleteAt = LocalDateTime.now()
        return contextRepository.save(context)
    }
}
