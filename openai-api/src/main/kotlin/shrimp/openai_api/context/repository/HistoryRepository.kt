package shrimp.openai_api.context.repository

import org.springframework.data.jpa.repository.JpaRepository
import shrimp.openai_api.context.entity.History
import java.util.*

interface HistoryRepository : JpaRepository<History, String> {
    fun findByContextIdOrderByCreateAt(contextId: UUID): List<History>
}