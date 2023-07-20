package shrimp.openai_api.context.dto.request

import shrimp.openai_api.context.entity.Context
import shrimp.openai_api.context.entity.History
import shrimp.openai_api.context.types.Name
import shrimp.openai_api.context.types.Role
import java.util.*

data class AddHistoryRequest(
    val id: UUID,
    val role: Role,
    val name: Name,
    val content: String
) {
    fun convertEntity(
        context: Context,
    ): History {
        return History(
            role = this.role,
            name = this.name,
            content = this.content,
            context = context,
        )
    }
}
