package shrimp.openai_kotlin.api.completion.service

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
import shrimp.openai_kotlin.api.completion.request.ChatCompletionRequest
import shrimp.openai_kotlin.api.completion.response.CompletionResponse
import shrimp.openai_kotlin.base.OpenAIClient

/**
 * @author 김회민
 * @since 2021-03-26
 */
@Suppress("ReactiveStreamsUnusedPublisher")
@DisplayName("Chat API Service Test")
class ChatCompletionServiceTest {

    companion object {
        private const val ID = "company-XXXXXXXXXXXXXXXXXXXXXXX"
    }

    private val mapper = ObjectMapper()
    private val webClient = mockk<WebClient>()
    private val openAIClient = OpenAIClient(webClient)
    private val service = CompletionService(openAIClient = openAIClient, objectMapper = mapper)

    private val expectedResponse = CompletionResponse(
        id = ID,
        obj = "chat.completion",
        model = ChatCompletionRequest.Model.GPT_3_5_TURBO.value,
        created = 1600000000
    )

    @Nested
    @DisplayName("비동기, 동기 요청")
    inner class General {

        @Nested
        @DisplayName("성공")
        inner class Success {

            private val request = ChatCompletionRequest(
                messages = listOf(ChatCompletionRequest.Message(content = "hello"))
            )

            private val response = expectedResponse.copy(
                usage = CompletionResponse.Usage(
                    completionTokens = 9,
                    promptTokens = 9,
                    totalTokens = 18
                ),
                choices = listOf(
                    CompletionResponse.Choice(
                        message = CompletionResponse.Choice.Message(
                            role = "assistant",
                            content = "hello"
                        ),
                        index = 0,
                        finishReason = "stop"
                    )
                )
            )

            private fun validatedCompletionResponse(
                response: CompletionResponse
            ) {
                assertThat(response.id).isNotNull
                assertThat(response.obj).isEqualTo("chat.completion")
                assertThat(response.model).isEqualTo(ChatCompletionRequest.Model.GPT_3_5_TURBO.value)
                assertThat(response.created).isNotNull

                assertThat(response.usage).isNotNull

                assertThat(response.choices).isNotNull
                assertThat(response.choices?.size).isGreaterThan(0)
                assertThat(response.choices?.get(0)?.text).isNull()
                assertThat(response.choices?.get(0)?.message).isNotNull
                assertThat(response.choices?.get(0)?.delta).isNull()
            }

            @BeforeEach
            fun setUp() {
                every {
                    webClient
                        .post()
                        .uri("/chat/completions")
                        .body(
                            any<Mono<ChatCompletionRequest>>(),
                            eq(ChatCompletionRequest::class.java)
                        )
                        .retrieve()
                        .bodyToMono(CompletionResponse::class.java)
                } returns Mono.just(response)
            }

            @Test
            @DisplayName("비동기")
            fun asyncTest() {
                StepVerifier
                    .create(service.postChatCompletionAsync(request))
                    .assertNext(this::validatedCompletionResponse)
                    .verifyComplete()
            }

            @Test
            @DisplayName("동기")
            fun blockTest() {
                val response = service.postChatCompletion(request)
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
            private val request = ChatCompletionRequest(
                messages = listOf(ChatCompletionRequest.Message(content = "hello")),
                stream = true
            )

            private val choice = CompletionResponse.Choice(index = 0)
            private val delta = CompletionResponse.Choice.Message()

            private val expectedEvents = listOf(
                expectedResponse.copy(
                    choices = listOf(
                        choice.copy(delta = delta.copy(role = "assistant"))
                    )
                ),
                expectedResponse.copy(
                    choices = listOf(
                        choice.copy(delta = delta.copy(content = "Hello"))
                    )
                ),
                expectedResponse.copy(
                    choices = listOf(
                        choice.copy(delta = delta.copy(content = "!"))
                    )
                ),
                expectedResponse.copy(
                    choices = listOf(
                        choice.copy(delta = delta.copy(content = " How"))
                    )
                ),
                expectedResponse.copy(
                    choices = listOf(
                        choice.copy(delta = delta.copy(content = " can"))
                    )
                ),
                expectedResponse.copy(
                    choices = listOf(
                        choice.copy(delta = delta.copy(content = " I"))
                    )
                ),
                expectedResponse.copy(
                    choices = listOf(
                        choice.copy(delta = delta.copy(content = " assist"))
                    )
                ),
                expectedResponse.copy(
                    choices = listOf(
                        choice.copy(delta = delta.copy(content = " you"))
                    )
                ),
                expectedResponse.copy(
                    choices = listOf(
                        choice.copy(delta = delta.copy(content = " today"))
                    )
                ),
                expectedResponse.copy(
                    choices = listOf(
                        choice.copy(delta = delta.copy(), finishReason = "stop")
                    )
                )
            )

            private val expectedEventStrings = expectedEvents.map {
                mapper.writeValueAsString(it)
            } + "[DONE]" // 마지막에는 [DONE] 이벤트가 추가로 발생

            @BeforeEach
            fun setUp() {
                every {
                    webClient
                        .post()
                        .uri("/chat/completions")
                        .body(
                            any<Mono<ChatCompletionRequest>>(),
                            eq(ChatCompletionRequest::class.java)
                        )
                        .retrieve()
                        .bodyToFlux(String::class.java)
                } returns Flux.fromIterable(expectedEventStrings)
            }

            @Test
            @DisplayName("스트리밍")
            fun streamTest() {
                StepVerifier
                    .create(service.postChatCompletionStream(request))
                    .expectNextSequence(expectedEvents)
                    .expectComplete()
                    .verify()
            }
        }
    }
}
