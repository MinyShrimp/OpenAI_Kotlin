package shrimp.openai_api.context.controller

import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.web.bind.annotation.*
import shrimp.openai_api.context.dto.request.AddHistoryRequest
import shrimp.openai_api.context.dto.response.AddHistoryResponse
import shrimp.openai_api.context.dto.response.GetHistoryResponse
import shrimp.openai_api.context.repository.ContextRepository
import shrimp.openai_api.context.repository.HistoryRepository
import java.util.*

@RestController
@RequestMapping("/api/context")
class HistoryController(
    private val historyRepository: HistoryRepository,
    private val contextRepository: ContextRepository
) {
    private val logger = KotlinLogging.logger {}

    @GetMapping("/{id}/history")
    fun getHistoryList(
        @PathVariable("id") contextId: UUID
    ): List<GetHistoryResponse> {
        val historyList = historyRepository.findByContextIdOrderByCreateAt(contextId)
        return historyList.map { GetHistoryResponse.of(it) }
    }

    @Transactional
    @PostMapping("/history/add")
    fun addHistory(
        @RequestBody dto: AddHistoryRequest
    ): AddHistoryResponse {
        val isExists = contextRepository.existsById(dto.id)
        if (!isExists) {
            throw IllegalArgumentException("Context not found")
        }

        val context = contextRepository.findById(dto.id).get()
        val history = dto.convertEntity(context)
        val savedHistory = historyRepository.save(history)
        return AddHistoryResponse.of(savedHistory)
    }
}