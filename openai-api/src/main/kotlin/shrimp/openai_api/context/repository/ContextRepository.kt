package shrimp.openai_api.context.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import shrimp.openai_api.context.entity.Context

@Repository
interface ContextRepository : JpaRepository<Context, String> {
    fun findAllByOrderByUpdateAtDesc(): List<Context>
}
