package shrimp.openai_api.context.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import shrimp.openai_api.context.entity.Context
import shrimp.openai_api.security.entity.Account
import java.util.*

@Repository
interface ContextRepository : JpaRepository<Context, UUID> {
    fun findAllByAccountOrderByUpdateAtDesc(account: Account): List<Context>
}
