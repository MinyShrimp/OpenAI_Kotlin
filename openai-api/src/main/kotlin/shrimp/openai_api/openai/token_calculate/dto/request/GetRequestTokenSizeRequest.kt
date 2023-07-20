package shrimp.openai_api.openai.token_calculate.dto.request

import shrimp.openai_core.api.completion.request.ChatCompletionRequest

data class GetRequestTokenSizeRequest(
    val model: ChatCompletionRequest.Model,
    val messages: List<ChatCompletionRequest.Message>
)
