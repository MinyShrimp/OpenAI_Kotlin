package shrimp.openai_api.context.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import shrimp.openai_api.context.entity.Prompt
import java.util.*

@Repository
interface PromptRepository : JpaRepository<Prompt, UUID> {
    fun findByContextIdOrderByOrderAsc(contextId: UUID): List<Prompt>
}
