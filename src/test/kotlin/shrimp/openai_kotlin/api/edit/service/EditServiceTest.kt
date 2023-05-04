package shrimp.openai_kotlin.api.edit.service

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import reactor.test.StepVerifier
import shrimp.openai_kotlin.api.edit.request.EditRequest
import shrimp.openai_kotlin.api.edit.response.EditResponse
import shrimp.openai_kotlin.base.OpenAIClient

/**
 * @author 김회민
 * @since 2021-03-26
 */
@Suppress("ReactiveStreamsUnusedPublisher")
@DisplayName("Edits API Service Test")
class EditServiceTest {

    private val webClient = mockk<WebClient>()
    private val openAIClient = OpenAIClient(webClient)
    private val service = EditService(openAIClient)

    @Nested
    @DisplayName("Edit 생성 - POST /edits")
    inner class PostCreateEdit {

        @Nested
        @DisplayName("성공")
        inner class Success {

            private val request = EditRequest(
                model = EditRequest.Model.TEXT,
                input = "What day of the wek is it?",
                instruction = "Fix the spelling mistakes"
            )

            private val response = EditResponse(
                obj = "edit",
                created = 1616784000,
                choices = listOf(
                    EditResponse.Choice(
                        text = "What day of the week is it?",
                        index = 0
                    )
                ),
                usage = EditResponse.Usage(
                    promptTokens = 10,
                    completionTokens = 10,
                    totalTokens = 20
                )
            )

            private fun validateEditResponse(response: EditResponse) {
                assertThat(response.obj).isEqualTo("edit")
                assertThat(response.created).isNotNull

                assertThat(response.choices).isInstanceOf(List::class.java)
                assertThat(response.choices).isNotEmpty
                assertThat(response.choices?.get(0)?.text ?: false).isEqualTo("What day of the week is it?")
                assertThat(response.choices?.get(0)?.index ?: false).isEqualTo(0)

                assertThat(response.usage).isNotNull
                assertThat(response.usage?.promptTokens).isNotNull
                assertThat(response.usage?.completionTokens).isNotNull
                assertThat(response.usage?.totalTokens).isNotNull
            }

            @BeforeEach
            fun setUp() {
                every {
                    webClient
                        .post()
                        .uri("/edits")
                        .body(
                            any<Mono<EditRequest>>(),
                            eq(EditRequest::class.java)
                        )
                        .retrieve()
                        .bodyToMono(EditResponse::class.java)
                } returns response.toMono()
            }

            @Test
            @DisplayName("비동기")
            fun async() {
                StepVerifier
                    .create(service.postCreateEditAsync(request))
                    .assertNext(this::validateEditResponse)
                    .verifyComplete()
            }

            @Test
            @DisplayName("동기")
            fun block() {
                validateEditResponse(service.postCreateEdit(request))
            }
        }
    }
}
