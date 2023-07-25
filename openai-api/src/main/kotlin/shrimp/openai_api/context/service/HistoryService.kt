package shrimp.openai_api.context.service

import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.stereotype.Service
import shrimp.openai_api.context.dto.request.AddHistoryRequest
import shrimp.openai_api.context.entity.History
import shrimp.openai_api.context.repository.ContextRepository
import shrimp.openai_api.context.repository.HistoryRepository
import java.util.*

@Service
class HistoryService(
    private val contextService: ContextService,
    private val historyRepository: HistoryRepository,
    private val contextRepository: ContextRepository,
) {
    private val logger = KotlinLogging.logger {}

    fun getHistoryList(
        contextId: UUID
    ): List<History> {
        return this.historyRepository.findByContextIdOrderByCreateAt(contextId)
    }

    @Transactional
    fun addHistory(
        token: String,
        dto: AddHistoryRequest
    ): History {
        val context = this.contextService.getContext(token, dto.id)
        val history = dto.convertEntity(context)
        val savedHistory = this.historyRepository.save(history)
        
        context.updateAt = savedHistory.createAt
        this.contextRepository.save(context)

        return savedHistory
    }
}
