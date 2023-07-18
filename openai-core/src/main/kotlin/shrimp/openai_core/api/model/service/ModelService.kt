package shrimp.openai_core.api.model.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import shrimp.openai_core.api.model.response.ModelListResponse
import shrimp.openai_core.api.model.response.ModelResponse
import shrimp.openai_core.base.OpenAIClient
import shrimp.openai_core.base.OpenAIOption

/**
 * Models API Service
 *
 * @author 김회민
 * @see <a href="https://platform.openai.com/docs/models">Models Overview</a>
 * @see <a href="https://platform.openai.com/docs/api-reference/models">Models API Document</a>
 * @since 2023-03-26
 */
@Service
class ModelService(
    private val openAIClient: OpenAIClient
) {

    /**
     * GET /models. Async.
     */
    fun getModelListAsync(
        option: OpenAIOption? = null
    ): Mono<ModelListResponse> {
        return openAIClient(option)
            .get()
            .uri("/models")
            .retrieve()
            .bodyToMono(ModelListResponse::class.java)
    }

    /**
     * GET /models. Block.
     */
    fun getModelList(
        option: OpenAIOption? = null
    ): ModelListResponse {
        return getModelListAsync(option).block()!!
    }

    /**
     * GET /models/{model_name}. Async.
     */
    fun getModelAsync(
        modelName: String,
        option: OpenAIOption? = null
    ): Mono<ModelResponse> {
        return openAIClient(option)
            .get()
            .uri("/models/$modelName")
            .retrieve()
            .bodyToMono(ModelResponse::class.java)
    }

    /**
     * GET /models/{model_name}. Block.
     */
    fun getModel(
        modelName: String,
        option: OpenAIOption? = null
    ): ModelResponse {
        return getModelAsync(modelName, option).block()!!
    }
}
