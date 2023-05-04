package shrimp.openai_kotlin.api.finetune.service

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import shrimp.openai_kotlin.api.file.response.FileResponse
import shrimp.openai_kotlin.api.finetune.request.FineTuneCreateRequest
import shrimp.openai_kotlin.api.finetune.response.FineTuneEventListResponse
import shrimp.openai_kotlin.api.finetune.response.FineTuneListResponse
import shrimp.openai_kotlin.api.finetune.response.FineTuneResponse
import shrimp.openai_kotlin.base.OpenAIClient

@Suppress("ReactiveStreamsUnusedPublisher")
@DisplayName("Fine-Tuning API Service Test")
class FineTuneServiceTest {

    companion object {
        private const val ORG_ID = "org-XXXXXXXXXXXXXXXXXXXXXXX"
        private const val FT_ID = "ft-1234567890"
    }

    private val webClient = mockk<WebClient>()
    private val openAIClient = OpenAIClient(webClient)
    private val service = FineTuneService(openAIClient)


    private val expectedFineTuneResponse = FineTuneResponse(
        id = FT_ID,
        organizationId = ORG_ID,
        obj = "fine-tune",
        model = FineTuneCreateRequest.Model.DAVINCI.value,
        fineTunedModel = "davinci:ft-personal-2023-03-27",
        status = "succeeded",
        hyperparams = FineTuneResponse.Hyperparams(),
        trainingFiles = listOf(),
        validationFiles = listOf(),
        resultFiles = listOf(),
        events = listOf(),
        createdAt = 1600000000,
        updatedAt = 1600000000,
    )

    @Nested
    @DisplayName("전체 조회 - GET /fine-tunes")
    inner class GetFineTuneList {

        @Nested
        @DisplayName("성공")
        inner class Success {

            private val response = FineTuneListResponse(
                data = listOf(),
                obj = "list"
            )

            private fun validatedResponse(
                response: FineTuneListResponse
            ) {
                assertThat(response).isNotNull
                assertThat(response.obj).isEqualTo("list")
                assertThat(response.data).isNotNull
            }

            @BeforeEach
            fun setUp() {
                every {
                    webClient.get()
                        .uri("/fine-tunes")
                        .retrieve()
                        .bodyToMono(FineTuneListResponse::class.java)
                } returns Mono.just(response)
            }

            @Test
            @DisplayName("비동기")
            fun getFineTuneListAsync() {
                StepVerifier
                    .create(service.getFineTuneListAsync())
                    .assertNext(this::validatedResponse)
                    .verifyComplete()
            }

            @Test
            @DisplayName("동기")
            fun getFineTuneList() {
                validatedResponse(service.getFineTuneList())
            }
        }
    }

    @Nested
    @DisplayName("단건 조회 - GET /fine-tunes/{id}")
    inner class GetFineTune {

        @Nested
        @DisplayName("성공")
        inner class Success {

            private val response = expectedFineTuneResponse.copy()

            private fun validatedResponse(
                response: FineTuneResponse
            ) {
                assertThat(response.id).isEqualTo(FT_ID)
                assertThat(response.organizationId).isNotNull
                assertThat(response.obj).isEqualTo("fine-tune")
                assertThat(response.model).isEqualTo(FineTuneCreateRequest.Model.DAVINCI.value)
                assertThat(response.trainingFiles).isNotNull
                assertThat(response.events).isNotNull
            }

            @BeforeEach
            fun setUp() {
                every {
                    webClient.get()
                        .uri("/fine-tunes/$FT_ID")
                        .retrieve()
                        .bodyToMono(FineTuneResponse::class.java)
                } returns Mono.just(response)
            }

            @Test
            @DisplayName("비동기")
            fun getFineTuneAsync() {
                StepVerifier
                    .create(service.getFineTuneAsync(FT_ID))
                    .assertNext(this::validatedResponse)
                    .verifyComplete()
            }

            @Test
            @DisplayName("동기")
            fun getFineTune() {
                validatedResponse(service.getFineTune(FT_ID))
            }
        }
    }

    @Nested
    @DisplayName("이벤트 조회 - GET /fine-tunes/{id}/events")
    inner class GetFineTuneEventList {

        @Nested
        @DisplayName("성공")
        inner class Success {

            private val response = FineTuneEventListResponse(
                data = listOf(),
                obj = "list"
            )

            private fun validatedResponse(
                response: FineTuneEventListResponse
            ) {
                assertThat(response).isNotNull
                assertThat(response.obj).isEqualTo("list")
                assertThat(response.data).isNotNull
            }

            @BeforeEach
            fun setUp() {
                every {
                    webClient.get()
                        .uri("/fine-tunes/$FT_ID/events")
                        .retrieve()
                        .bodyToMono(FineTuneEventListResponse::class.java)
                } returns Mono.just(response)
            }

            @Test
            @DisplayName("비동기")
            fun getFineTuneEventListAsync() {
                StepVerifier
                    .create(service.getFineTuneEventListAsync(FT_ID))
                    .assertNext(this::validatedResponse)
                    .verifyComplete()
            }

            @Test
            @DisplayName("동기")
            fun getFineTuneEventList() {
                validatedResponse(service.getFineTuneEventList(FT_ID))
            }
        }
    }

    @Nested
    @DisplayName("생성 - POST /fine-tunes")
    inner class PostCreateFineTune {

        @Nested
        @DisplayName("성공")
        inner class Success {

            private val request = FineTuneCreateRequest(
                model = FineTuneCreateRequest.Model.DAVINCI,
                trainingFile = "file-1234567890",
            )

            private val response = expectedFineTuneResponse.copy(
                status = "pending",
                trainingFiles = listOf(
                    FileResponse(
                        id = "file-1234567890",
                        obj = "file",
                        purpose = "fine-tune",
                        filename = "my-data-train.jsonl",
                        bytes = 1234567890,
                        createdAt = 1600000000,
                    )
                ),
                events = listOf(
                    FineTuneEventListResponse.Event(
                        obj = "fine-tune-event",
                        level = "info",
                        createdAt = 1600000000,
                        message = "Job enqueued. Waiting for jobs ahead to complete. Queue number: 0."
                    )
                )
            )

            private fun validatedResponse(
                response: FineTuneResponse
            ) {
                assertThat(response.id).isNotNull
                assertThat(response.organizationId).isNotNull
                assertThat(response.obj).isEqualTo("fine-tune")
                assertThat(response.model).isEqualTo(FineTuneCreateRequest.Model.DAVINCI.value)

                assertThat(response.trainingFiles).isNotNull
                assertThat(response.trainingFiles?.size).isGreaterThan(0)

                assertThat(response.events).isNotNull
                assertThat(response.events?.size).isGreaterThan(0)
            }

            @BeforeEach
            fun setUp() {
                every {
                    webClient.post()
                        .uri("/fine-tunes")
                        .body(
                            any<Mono<FineTuneCreateRequest>>(),
                            eq(FineTuneCreateRequest::class.java)
                        )
                        .retrieve()
                        .bodyToMono(FineTuneResponse::class.java)
                } returns Mono.just(response)
            }

            @Test
            @DisplayName("비동기")
            fun postCreateFineTuneAsync() {
                StepVerifier
                    .create(service.postCreateFineTuneAsync(request))
                    .assertNext(this::validatedResponse)
                    .verifyComplete()
            }

            @Test
            @DisplayName("동기")
            fun postCreateFineTune() {
                validatedResponse(service.postCreateFineTune(request))
            }
        }
    }

    @Nested
    @DisplayName("취소 - POST /fine-tunes/{id}/cancel")
    inner class PostCancelFineTune {

        @Nested
        @DisplayName("성공")
        inner class Success {

            private val response = expectedFineTuneResponse.copy(
                status = "cancelled"
            )

            private fun validatedResponse(
                response: FineTuneResponse
            ) {
                assertThat(response.id).isEqualTo(FT_ID)
                assertThat(response.organizationId).isNotNull
                assertThat(response.obj).isEqualTo("fine-tune")
                assertThat(response.model).isEqualTo(FineTuneCreateRequest.Model.DAVINCI.value)
                assertThat(response.trainingFiles).isNotNull
                assertThat(response.events).isNotNull
                assertThat(response.status).isEqualTo("cancelled")
            }

            @BeforeEach
            fun setUp() {
                every {
                    webClient.post()
                        .uri("/fine-tunes/$FT_ID/cancel")
                        .retrieve()
                        .bodyToMono(FineTuneResponse::class.java)
                } returns Mono.just(response)
            }

            @Test
            @DisplayName("비동기")
            fun postCancelFineTuneAsync() {
                StepVerifier
                    .create(service.postCancelFineTuneAsync(FT_ID))
                    .assertNext(this::validatedResponse)
                    .verifyComplete()
            }

            @Test
            @DisplayName("동기")
            fun postCancelFineTune() {
                validatedResponse(service.postCancelFineTune(FT_ID))
            }
        }
    }
}
