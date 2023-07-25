package shrimp.openai_api.context.service

import mu.KotlinLogging
import org.springframework.stereotype.Service
import shrimp.openai_api.context.entity.Prompt
import shrimp.openai_api.context.repository.PromptRepository
import java.util.*

@Service
class PromptService(
    private val promptRepository: PromptRepository
) {
    private val logger = KotlinLogging.logger {}

    fun getPromptList(
        contextId: UUID
    ): List<Prompt> {
        return this.promptRepository.findByContextIdOrderByOrderAsc(contextId)
    }
}
