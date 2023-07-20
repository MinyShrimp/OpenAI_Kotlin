package shrimp.openai_api.openai.token_calculate.dto.request

import shrimp.openai_core.api.completion.request.ChatCompletionRequest

data class GetContentTokenSizeRequest(
    val model: ChatCompletionRequest.Model,
    val content: String
)
