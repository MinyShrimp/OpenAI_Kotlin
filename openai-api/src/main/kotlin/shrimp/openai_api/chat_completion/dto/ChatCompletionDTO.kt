package shrimp.openai_api.chat_completion.dto

import shrimp.openai_core.api.completion.entity.Role
import shrimp.openai_core.api.completion.request.ChatCompletionRequest

data class ChatCompletionDTO(
    val model: ChatCompletionRequest.Model,
    val messages: List<MessageDTO>
) {
    data class MessageDTO(
        val role: Role,
        val name: String?,
        val content: String
    )

    fun convert(
        stream: Boolean = false
    ): ChatCompletionRequest {
        val msgList = this.messages.map {
            ChatCompletionRequest.Message(
                role = it.role,
                content = it.content,
                name = it.name
            )
        }

        return ChatCompletionRequest(
            model = this.model,
            messages = msgList,
            stream = stream
        )
    }
}