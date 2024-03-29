package shrimp.openai_api.openai.model

import mu.KotlinLogging
import org.springframework.web.bind.annotation.*
import shrimp.openai_core.api.model.response.ModelResponse
import shrimp.openai_core.api.model.service.ModelService
import shrimp.openai_core.base.OpenAIOption

@RestController
@RequestMapping("/api/model")
class ModelController(
    val modelService: ModelService
) {
    private val logger = KotlinLogging.logger {}

    @GetMapping("/list")
    fun getModels(
        @RequestHeader("Authorization") auth: String,
    ): List<ModelResponse> {
        val resp = modelService.getModelList(
            OpenAIOption(auth.replace("Bearer ", ""))
        )
        return resp.data ?: throw Exception("No data")
    }
}