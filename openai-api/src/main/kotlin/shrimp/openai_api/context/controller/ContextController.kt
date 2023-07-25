package shrimp.openai_api.context.controller

import mu.KotlinLogging
import org.springframework.web.bind.annotation.*
import shrimp.openai_api.context.dto.request.CreateContextRequest
import shrimp.openai_api.context.dto.request.UpdateContextRequest
import shrimp.openai_api.context.dto.response.*
import shrimp.openai_api.context.service.ContextService
import shrimp.openai_api.security.service.CookieService
import java.util.*

@RestController
@RequestMapping("/api/context")
class ContextController(
    private val contextService: ContextService,
) {
    private val logger = KotlinLogging.logger {}

    @GetMapping
    fun getContextList(
        @CookieValue(CookieService.COOKIE_NAME) token: String
    ): List<ContextResponse> {
        val contextList = this.contextService.getContextList(token)
        return contextList.map { ContextResponse.of(it) }
    }

    @PostMapping
    fun createContext(
        @RequestBody dto: CreateContextRequest,
        @CookieValue(CookieService.COOKIE_NAME) token: String
    ): ContextResponse {
        val context = this.contextService.createContext(token, dto)
        return ContextResponse.of(context)
    }

    @PutMapping
    fun updateContext(
        @RequestBody dto: UpdateContextRequest,
        @CookieValue(CookieService.COOKIE_NAME) token: String
    ): ContextResponse {
        val context = this.contextService.updateContext(token, dto)
        return ContextResponse.of(context)
    }

    @DeleteMapping("/{id}")
    fun deleteContext(
        @PathVariable("id") contextId: UUID,
        @CookieValue(CookieService.COOKIE_NAME) token: String
    ) {
        this.contextService.deleteContext(token, contextId)
    }
}
