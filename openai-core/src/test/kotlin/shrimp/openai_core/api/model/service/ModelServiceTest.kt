package shrimp.openai_core.api.model.service

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.WebClient
import reactor.kotlin.core.publisher.toMono
import reactor.test.StepVerifier
import shrimp.openai_core.api.model.response.ModelListResponse
import shrimp.openai_core.api.model.response.ModelResponse
import shrimp.openai_core.base.OpenAIClient

/**
 * @author 김회민
 * @since 2023-03-26
 */
@Suppress("ReactiveStreamsUnusedPublisher")
@DisplayName("Models API Service Test")
class ModelServiceTest {

    companion object {
        private const val MODEL_NAME = "ada"
    }

    private val webClient = mockk<WebClient>()
    private val openAIClient = OpenAIClient(webClient)
    private val modelService = ModelService(openAIClient)

    @Nested
    @DisplayName("전체 모델 조회 - GET /models")
    inner class ModelList {

        @Nested
        @DisplayName("성공")
        inner class Success {

            private val response = ModelListResponse(
                obj = "list",
                data = listOf()
            )

            private fun validateModelListResponse(
                response: ModelListResponse
            ) {
                assertThat(response).isNotNull
                assertThat(response.obj).isEqualTo("list")
                assertThat(response.data).isNotNull
            }

            @BeforeEach
            fun setUp() {
                every {
                    webClient.get()
                        .uri("/models")
                        .retrieve()
                        .bodyToMono(ModelListResponse::class.java)
                } returns response.toMono()
            }

            @Test
            @DisplayName("비동기")
            fun async() {
                StepVerifier
                    .create(modelService.getModelListAsync())
                    .assertNext(this::validateModelListResponse)
                    .verifyComplete()
            }

            @Test
            @DisplayName("동기")
            fun block() {
                validateModelListResponse(modelService.getModelList())
            }
        }
    }

    @Nested
    @DisplayName("특정 모델 조회 - GET /models/{model_id}")
    inner class RetrieveModel {

        @Nested
        @DisplayName("성공")
        inner class Success {

            private val response = ModelResponse(
                id = MODEL_NAME,
                obj = "model"
            )

            private fun validateModelResponse(
                response: ModelResponse
            ) {
                assertThat(response.id).isEqualTo(MODEL_NAME)
                assertThat(response.obj).isEqualTo("model")
            }

            @BeforeEach
            fun setUp() {
                every {
                    webClient.get()
                        .uri("/models/$MODEL_NAME")
                        .retrieve()
                        .bodyToMono(ModelResponse::class.java)
                } returns response.toMono()
            }

            @Test
            @DisplayName("비동기")
            fun async() {
                StepVerifier
                    .create(modelService.getModelAsync(MODEL_NAME))
                    .assertNext(this::validateModelResponse)
                    .verifyComplete()
            }

            @Test
            @DisplayName("동기")
            fun block() {
                validateModelResponse(modelService.getModel(MODEL_NAME))
            }
        }
    }
}
