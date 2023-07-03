package shrimp.openai_core.api.completion.service

import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import shrimp.openai_core.base.OpenAIClient

/**
 * @author 김회민
 * @since 2021-03-26
 */
@Suppress("ReactiveStreamsUnusedPublisher")
@DisplayName("Completion API Service Test")
class CompletionServiceTest {

    companion object {
        private const val ID = "company-XXXXXXXXXXXXXXXXXXXXXXX"
    }

    private val mapper = ObjectMapper()
    private val webClient = mockk<WebClient>()
    private val openAIClient = OpenAIClient(webClient)
    private val service = CompletionService(
        openAIClient = openAIClient,
        objectMapper = mapper
    )

    private val expectedResponse = shrimp.openai_core.api.completion.response.CompletionResponse(
        id = ID,
        obj = "text_completion",
        model = shrimp.openai_core.api.completion.request.CompletionRequest.Model.DAVINCI_003.value,
        created = 1600000000
    )

    @Nested
    @DisplayName("비동기, 동기 요청")
    inner class General {

        @Nested
        @DisplayName("성공")
        inner class Success {

            private val request = shrimp.openai_core.api.completion.request.CompletionRequest(
                prompt = listOf("This is a test")
            )

            private val response = expectedResponse.copy(
                choices = listOf(
                    shrimp.openai_core.api.completion.response.CompletionResponse.Choice(
                        text = "This is a test prompt",
                        index = 0,
                        logprobs = null,
                        finishReason = null
                    )
                ),
                usage = shrimp.openai_core.api.completion.response.CompletionResponse.Usage(
                    completionTokens = 16,
                    promptTokens = 4,
                    totalTokens = 20
                )
            )

            private fun validatedCompletionResponse(
                response: shrimp.openai_core.api.completion.response.CompletionResponse
            ) {
                assertThat(response.id).isNotNull
                assertThat(response.obj).isEqualTo("text_completion")
                assertThat(response.model).isEqualTo(shrimp.openai_core.api.completion.request.CompletionRequest.Model.DAVINCI_003.value)
                assertThat(response.created).isNotNull
                assertThat(response.usage).isNotNull
                assertThat(response.choices).isNotNull
                assertThat(response.choices?.size).isGreaterThan(0)
            }

            @BeforeEach
            fun setUp() {
                every {
                    webClient
                        .post()
                        .uri("/completions")
                        .body(
                            any<Mono<shrimp.openai_core.api.completion.request.CompletionRequest>>(),
                            eq(shrimp.openai_core.api.completion.request.CompletionRequest::class.java)
                        )
                        .retrieve()
                        .bodyToMono(shrimp.openai_core.api.completion.response.CompletionResponse::class.java)
                } returns Mono.just(response)
            }

            @Test
            @DisplayName("비동기")
            fun asyncTest() {
                StepVerifier.create(service.postCompletionAsync(request))
                    .assertNext(this::validatedCompletionResponse)
                    .verifyComplete()
            }

            @Test
            @DisplayName("동기")
            fun blockTest() {
                val response = service.postCompletion(request)
                validatedCompletionResponse(response)
            }
        }
    }

    @Nested
    @DisplayName("스트리밍 요청")
    inner class Streaming {

        @Nested
        @DisplayName("성공")
        inner class Success {

            private val request = shrimp.openai_core.api.completion.request.CompletionRequest(
                prompt = listOf("This is a test"),
                stream = true
            )

            private val choice = shrimp.openai_core.api.completion.response.CompletionResponse.Choice(index = 0)

            private val expectedEvents = listOf(
                expectedResponse.copy(choices = listOf(choice.copy(text = " **"))),
                expectedResponse.copy(choices = listOf(choice.copy(text = "\\n"))),
                expectedResponse.copy(choices = listOf(choice.copy(text = "\\n"))),
                expectedResponse.copy(choices = listOf(choice.copy(text = "This"))),
                expectedResponse.copy(choices = listOf(choice.copy(text = " is"))),
                expectedResponse.copy(choices = listOf(choice.copy(text = " a"))),
                expectedResponse.copy(choices = listOf(choice.copy(text = " test"))),
                expectedResponse.copy(choices = listOf(choice.copy(text = ".")))
            )

            private val expectedEventStrings = expectedEvents.map {
                mapper.writeValueAsString(it)
            } + "[DONE]" // 마지막에는 [DONE] 이벤트가 추가로 발생

            @BeforeEach
            fun setUp() {
                every {
                    webClient
                        .post()
                        .uri("/completions")
                        .body(
                            any<Mono<shrimp.openai_core.api.completion.request.CompletionRequest>>(),
                            eq(shrimp.openai_core.api.completion.request.CompletionRequest::class.java)
                        )
                        .retrieve()
                        .bodyToFlux(String::class.java)
                } returns Flux.fromIterable(expectedEventStrings)
            }

            @Test
            @DisplayName("스트리밍")
            fun streamTest() {
                StepVerifier.create(service.postCompletionStream(request))
                    .expectNextSequence(expectedEvents)
                    .expectComplete()
                    .verify()
            }
        }
    }
}
