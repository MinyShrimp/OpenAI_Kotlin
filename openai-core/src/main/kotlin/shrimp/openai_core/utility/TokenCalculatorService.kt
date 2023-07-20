package shrimp.openai_core.utility

import com.knuddels.jtokkit.Encodings
import com.knuddels.jtokkit.api.ModelType
import org.springframework.stereotype.Service
import shrimp.openai_core.api.completion.request.ChatCompletionRequest

@Service
class TokenCalculatorService {
    private val registry = Encodings.newDefaultEncodingRegistry()

    fun getRequestTokenSize(
        model: ChatCompletionRequest.Model,
        messages: List<ChatCompletionRequest.Message>,
    ): List<Int> {
        val modelType = ModelType.fromName(model.value).get()
        val encoding = registry.getEncodingForModel(modelType)

        val tokensPerName = 1
        val tokensPerMessage = 3
        val defaultTokenSize = 3

        //@formatter:off
        return messages.map {msg -> (
            defaultTokenSize
                + tokensPerMessage
                + encoding.countTokens(msg.content)
                + encoding.countTokens(msg.role.value)
                + if (msg.name != null) encoding.countTokens(msg.name) + tokensPerName else 0
        )}
        //@formatter:on
    }

    fun getTokenSize(
        model: ChatCompletionRequest.Model,
        content: String
    ): Int {
        val modelType = ModelType.fromName(model.value).get()
        val encoding = registry.getEncodingForModel(modelType)
        return encoding.countTokens(content)
    }
}
