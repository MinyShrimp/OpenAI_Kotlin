package shrimp.openai_kotlin.api.embedding.service

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
import shrimp.openai_kotlin.api.embedding.request.EmbeddingRequest
import shrimp.openai_kotlin.api.embedding.response.EmbeddingResponse
import shrimp.openai_kotlin.base.OpenAIClient

/**
 * @author 김회민
 * @since 2021-03-26
 */
@Suppress("ReactiveStreamsUnusedPublisher")
@DisplayName("Embeddings API Service Test")
class EmbeddingServiceTest {

    private val webClient = mockk<WebClient>()
    private val openAIClient = OpenAIClient(webClient)
    private val service = EmbeddingService(openAIClient)

    @Nested
    @DisplayName("임베딩 생성 - POST /embeddings")
    inner class PostEmbeddings {

        @Nested
        @DisplayName("성공")
        inner class Success {

            private val request = EmbeddingRequest(
                model = EmbeddingRequest.Model.EMBEDDING_ADA_002,
                input = listOf("Your text string goes here")
            )
            private val response = EmbeddingResponse(
                model = "text-embedding-ada-002-v2",
                obj = "list",
                usage = EmbeddingResponse.Usage(),
                data = listOf(
                    EmbeddingResponse.Data(
                        index = 0,
                        obj = "embedding",
                        embedding = listOf()
                    )
                )
            )

            private fun validatedEmbeddingResponse(
                response: EmbeddingResponse
            ) {
                assertThat(response).isNotNull
                assertThat(response.model).isEqualTo("text-embedding-ada-002-v2")
                assertThat(response.obj).isEqualTo("list")
                assertThat(response.usage).isNotNull
                assertThat(response.data).isNotNull
            }

            @BeforeEach
            fun setUp() {
                every {
                    webClient
                        .post()
                        .uri("/embeddings")
                        .body(
                            any<Mono<EmbeddingRequest>>(),
                            eq(EmbeddingRequest::class.java)
                        )
                        .retrieve()
                        .bodyToMono(EmbeddingResponse::class.java)
                } returns response.toMono()
            }

            @Test
            @DisplayName("비동기")
            fun async() {
                val monoEmbedding = service.postCreateEmbeddingsAsync(request)
                StepVerifier.create(monoEmbedding)
                    .assertNext(this::validatedEmbeddingResponse)
                    .verifyComplete()
            }

            @Test
            @DisplayName("동기")
            fun block() {
                val response = service.postCreateEmbeddings(request)
                validatedEmbeddingResponse(response)
            }
        }
    }
}
