package shrimp.openai_api.context.controller

import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.web.bind.annotation.*
import shrimp.openai_api.context.dto.request.AddHistoryRequest
import shrimp.openai_api.context.dto.response.HistoryResponse
import shrimp.openai_api.context.service.HistoryService
import shrimp.openai_api.security.service.CookieService
import java.util.*

@RestController
@RequestMapping("/api/context")
class HistoryController(
    private val historyService: HistoryService
) {
    private val logger = KotlinLogging.logger {}

    @GetMapping("/{id}/history")
    fun getHistoryList(
        @PathVariable("id") contextId: UUID,
        @CookieValue(CookieService.COOKIE_NAME) token: String
    ): List<HistoryResponse> {
        val historyList = this.historyService.getHistoryList(contextId)
        return historyList.map { HistoryResponse.of(it) }
    }

    @Transactional
    @PostMapping("/history/add")
    fun addHistory(
        @RequestBody dto: AddHistoryRequest,
        @CookieValue(CookieService.COOKIE_NAME) token: String
    ): HistoryResponse {
        val history = this.historyService.addHistory(token, dto)
        return HistoryResponse.of(history)
    }
}
