package shrimp.openai_kotlin.api.model.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import shrimp.openai_kotlin.api.model.response.ModelListResponse
import shrimp.openai_kotlin.api.model.response.ModelResponse
import shrimp.openai_kotlin.base.OpenAIClient

/**
 * Models API Service
 *
 * @author 김회민
 * @see <a href="https://platform.openai.com/docs/models">Models Overview</a>
 * @see <a href="https://platform.openai.com/docs/api-reference/models">Models API Document</a>
 * @since 2023-03-26
 */
@Service
class ModelService(private val openAIClient: OpenAIClient) {

    /**
     * GET /models. Async.
     */
    fun getModelListAsync(): Mono<ModelListResponse> {
        return openAIClient()
            .get()
            .uri("/models")
            .retrieve()
            .bodyToMono(ModelListResponse::class.java)
    }

    /**
     * GET /models. Block.
     */
    fun getModelList(): ModelListResponse {
        return getModelListAsync().block()!!
    }

    /**
     * GET /models/{model_name}. Async.
     */
    fun getModelAsync(
        modelName: String
    ): Mono<ModelResponse> {
        return openAIClient()
            .get()
            .uri("/models/$modelName")
            .retrieve()
            .bodyToMono(ModelResponse::class.java)
    }

    /**
     * GET /models/{model_name}. Block.
     */
    fun getModel(
        modelName: String
    ): ModelResponse {
        return getModelAsync(modelName).block()!!
    }
}
