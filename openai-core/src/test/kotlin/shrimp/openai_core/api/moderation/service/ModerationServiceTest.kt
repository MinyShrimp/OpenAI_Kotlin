package shrimp.openai_core.api.moderation.service

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import reactor.test.StepVerifier
import shrimp.openai_core.api.moderation.request.ModerationRequest
import shrimp.openai_core.api.moderation.response.ModerationResponse
import shrimp.openai_core.base.OpenAIClient

/**
 * @author 김회민
 * @since 2021-03-26
 */
@Suppress("ReactiveStreamsUnusedPublisher")
@DisplayName("Moderation API Service Test")
class ModerationServiceTest {

    companion object {
        private const val ID = "modr-XXXXXX"
    }

    private val webClient = mockk<WebClient>()
    private val openAIClient = OpenAIClient(webClient)
    private val service = ModerationService(openAIClient)

    @Nested
    @DisplayName("문장 검증 - POST /moderations")
    inner class PostCreateModeration {

        @Nested
        @DisplayName("정상 검증")
        inner class Normal {

            private val request = ModerationRequest(
                model = ModerationRequest.Model.LATEST,
                input = listOf("I love you."),
            )

            private val response = ModerationResponse(
                id = ID,
                model = ModerationRequest.Model.LATEST.value,
                results = listOf(
                    ModerationResponse.Result(
                        categories = ModerationResponse.Result.Categories(),
                        categoryScores = ModerationResponse.Result.CategoryScores(),
                        flagged = false
                    )
                ),
            )

            @BeforeEach
            fun setUp() {
                every {
                    webClient
                        .post()
                        .uri("/moderations")
                        .body(
                            any<Mono<ModerationRequest>>(),
                            eq(ModerationRequest::class.java)
                        )
                        .retrieve()
                        .bodyToMono(ModerationResponse::class.java)
                } returns response.toMono()
            }

            private fun validateModerationResponse(
                moderationResponse: ModerationResponse
            ) {
                assertThat(moderationResponse).isNotNull
                assertThat(moderationResponse).isInstanceOf(ModerationResponse::class.java)

                assertThat(moderationResponse.id).isNotNull
                assertThat(moderationResponse.model).isNotNull
                assertThat(moderationResponse.results).isInstanceOf(List::class.java)
                assertThat(moderationResponse.results).hasSize(1)
                assertThat(moderationResponse.results?.get(0)?.flagged ?: false).isFalse
            }

            @Test
            @DisplayName("비동기")
            fun async() {
                StepVerifier
                    .create(service.postCreateModerationAsync(request))
                    .assertNext(this::validateModerationResponse)
                    .verifyComplete()
            }

            @Test
            @DisplayName("동기")
            fun block() {
                validateModerationResponse(service.postCreateModeration(request))
            }
        }

        @Nested
        @DisplayName("폭력 검증")
        inner class Flagged {

            private val request = ModerationRequest(
                model = ModerationRequest.Model.LATEST,
                input = listOf("I want to kill them."),
            )

            private val response = ModerationResponse(
                id = ID,
                model = ModerationRequest.Model.LATEST.value,
                results = listOf(
                    ModerationResponse.Result(
                        categories = ModerationResponse.Result.Categories(),
                        categoryScores = ModerationResponse.Result.CategoryScores(),
                        flagged = true
                    )
                ),
            )

            @BeforeEach
            fun setUp() {
                every {
                    webClient
                        .post()
                        .uri("/moderations")
                        .body(
                            any<Mono<ModerationRequest>>(),
                            eq(ModerationRequest::class.java)
                        )
                        .retrieve()
                        .bodyToMono(ModerationResponse::class.java)
                } returns response.toMono()
            }

            private fun validateModerationResponse(
                moderationResponse: ModerationResponse
            ) {
                assertThat(moderationResponse).isNotNull
                assertThat(moderationResponse).isInstanceOf(ModerationResponse::class.java)

                assertThat(moderationResponse.id).isNotNull
                assertThat(moderationResponse.model).isNotNull
                assertThat(moderationResponse.results).isInstanceOf(List::class.java)
                assertThat(moderationResponse.results).hasSize(1)
                assertThat(moderationResponse.results?.get(0)?.flagged ?: false).isTrue
            }

            @Test
            @DisplayName("비동기")
            fun async() {
                StepVerifier
                    .create(service.postCreateModerationAsync(request))
                    .assertNext(this::validateModerationResponse)
                    .verifyComplete()
            }

            @Test
            @DisplayName("동기")
            fun block() {
                validateModerationResponse(service.postCreateModeration(request))
            }
        }
    }
}
