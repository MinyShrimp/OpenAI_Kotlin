package shrimp.openai_api.security.dto

import shrimp.openai_api.security.entity.Account
import shrimp.openai_api.security.entity.AccountRole
import java.time.LocalDateTime

data class AccountResponse(
    val name: String,
    val email: String,
    val roles: Set<AccountRole>,
    val createAt: LocalDateTime,
    val updateAt: LocalDateTime
) {
    companion object {
        fun of(
            entity: Account
        ): AccountResponse {
            return AccountResponse(
                name = entity.name,
                email = entity.email,
                roles = entity.roles,
                createAt = entity.createAt,
                updateAt = entity.updateAt
            )
        }
    }
}
