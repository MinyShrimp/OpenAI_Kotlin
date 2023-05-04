package shrimp.openai_kotlin.api.file.service

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientRequestException
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import shrimp.openai_kotlin.api.file.request.FileUploadRequest
import shrimp.openai_kotlin.api.file.response.FileDeleteResponse
import shrimp.openai_kotlin.api.file.response.FileListResponse
import shrimp.openai_kotlin.api.file.response.FileResponse
import shrimp.openai_kotlin.base.OpenAIClient
import java.net.URI
import java.nio.charset.StandardCharsets
import java.nio.file.NoSuchFileException

/**
 * @author 김회민
 * @since 2021-03-26
 */
@Suppress("ReactiveStreamsUnusedPublisher")
@DisplayName("Files API Service Test")
class FileServiceTest {

    private val webClient = mockk<WebClient>()
    private val openAIClient = OpenAIClient(webClient)
    private val service = FileService(openAIClient)

    @Nested
    @DisplayName("업로드된 모든 파일 정보 - GET /files")
    inner class GetFileList {

        @Nested
        @DisplayName("성공")
        inner class Success {

            private val expected = FileListResponse(
                obj = "list",
                data = listOf()
            )

            private fun validatedFileListResponse(
                response: FileListResponse
            ) {
                assertThat(response.obj).isEqualTo("list")
                assertThat(response.data).isNotNull
            }

            @BeforeEach
            fun setUp() {
                every {
                    webClient
                        .get()
                        .uri("/files")
                        .retrieve()
                        .bodyToMono(FileListResponse::class.java)
                } returns Mono.just(expected)
            }

            @Test
            @DisplayName("비동기")
            fun getFileListAsync() {
                StepVerifier.create(service.getFileListAsync())
                    .assertNext(this::validatedFileListResponse)
                    .verifyComplete()
            }

            @Test
            @DisplayName("동기")
            fun getFileListBlocking() {
                val response = service.getFileList()
                validatedFileListResponse(response)
            }
        }
    }

    @Nested
    @DisplayName("업로드된 특정 파일 정보 - GET /files/{id}")
    inner class GetFile {

        @Nested
        @DisplayName("성공")
        inner class Success {

            private val expected = FileResponse(
                id = "file-m4c88kO1AA9Ekg6hkJlx16uU",
                obj = "file",
                filename = "compiled_results.csv",
                purpose = "fine-tune-results",
                bytes = 37734,
                createdAt = 1678461870,
                status = "processed"
            )

            private fun validatedFileResponse(
                response: FileResponse
            ) {
                assertThat(response)
                    .usingRecursiveComparison()
                    .isEqualTo(expected)
            }

            @BeforeEach
            fun setUp() {
                every {
                    webClient
                        .get()
                        .uri("/files/${expected.id}")
                        .retrieve()
                        .bodyToMono(FileResponse::class.java)
                } returns Mono.just(expected)
            }

            @Test
            @DisplayName("비동기")
            fun getFileAsync() {
                StepVerifier
                    .create(service.getFileAsync("file-m4c88kO1AA9Ekg6hkJlx16uU"))
                    .assertNext(::validatedFileResponse)
                    .verifyComplete()
            }

            @Test
            @DisplayName("동기")
            fun getFileBlocking() {
                val response = service.getFile("file-m4c88kO1AA9Ekg6hkJlx16uU")
                validatedFileResponse(response)
            }
        }

        @Nested
        @DisplayName("실패: ID 없음")
        inner class NoMatchedID {

            @BeforeEach
            fun setUp() {
                every {
                    webClient
                        .get()
                        .uri("/files/noMatch")
                        .retrieve()
                        .bodyToMono(FileResponse::class.java)
                } answers {
                    Mono.error(
                        WebClientResponseException.create(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.reasonPhrase,
                            HttpHeaders.EMPTY,
                            byteArrayOf(),
                            StandardCharsets.UTF_8
                        )
                    )
                }
            }

            @Test
            @DisplayName("비동기")
            fun async() {
                StepVerifier
                    .create(service.getFileAsync("noMatch"))
                    .expectError(WebClientResponseException.NotFound::class.java)
                    .verify()
            }

            @Test
            @DisplayName("동기")
            fun blocking() {
                Assertions.assertThatThrownBy { service.getFile("noMatch") }
                    .isInstanceOf(WebClientResponseException.NotFound::class.java)
                    .hasMessageContaining("404")
            }
        }
    }

    @Nested
    @DisplayName("파일 컨텐츠 가져오기 - GET /files/{id}/content")
    inner class GetFileContent {

        @Nested
        @DisplayName("성공")
        inner class Success {

            private val fileId = "file-m4c88kO1AA9Ekg6hkJlx16uU"

            private fun validatedFileContentResponse(
                response: String
            ) {
                assertThat(response).isNotNull
            }

            @BeforeEach
            fun setUp() {
                every {
                    webClient
                        .get()
                        .uri("/files/${fileId}/content")
                        .retrieve()
                        .bodyToMono(String::class.java)
                } returns Mono.just("file content")
            }

            @Test
            @DisplayName("비동기")
            fun async() {
                StepVerifier
                    .create(service.getFileContentAsync(fileId))
                    .assertNext(this::validatedFileContentResponse)
                    .verifyComplete()
            }

            @Test
            @DisplayName("동기")
            fun blocking() {
                val response = service.getFileContent(fileId)
                validatedFileContentResponse(response)
            }
        }

        @Nested
        @DisplayName("실패: ID 없음")
        inner class NoMatchedID {

            @BeforeEach
            fun setUp() {
                every {
                    webClient
                        .get()
                        .uri("/files/noMatch/content")
                        .retrieve()
                        .bodyToMono(String::class.java)
                } answers {
                    Mono.error(
                        WebClientResponseException.create(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.reasonPhrase,
                            HttpHeaders.EMPTY,
                            byteArrayOf(),
                            StandardCharsets.UTF_8
                        )
                    )
                }
            }

            @Test
            @DisplayName("비동기")
            fun async() {
                StepVerifier
                    .create(service.getFileContentAsync("noMatch"))
                    .expectError(WebClientResponseException.NotFound::class.java)
                    .verify()
            }

            @Test
            @DisplayName("동기")
            fun blocking() {
                Assertions.assertThatThrownBy { service.getFileContent("noMatch") }
                    .isInstanceOf(WebClientResponseException.NotFound::class.java)
                    .hasMessageContaining("404")
            }
        }
    }

    @Nested
    @DisplayName("파일 업로드 - POST /files")
    inner class PostFileUpload {

        @Nested
        @DisplayName("성공")
        inner class Success {

            private val request = FileUploadRequest(
                purpose = "fine-tune",
                filename = "src/test/resources/openai/test.jsonl",
                uploadFilename = "test.jsonl"
            )

            private val expected = FileResponse(
                id = "file-m4c88kO1AA9Ekg6hkJlx16uU",
                obj = "file",
                filename = "test.jsonl",
                purpose = "fine-tune",
                bytes = 37734,
                createdAt = 1678461870,
                status = "processed"
            )

            private fun validatedFileResponse(
                response: FileResponse
            ) {
                assertThat(response.id).isNotNull
                assertThat(response.bytes).isNotNull
                assertThat(response.createdAt).isNotNull
                assertThat(response.status).isIn("uploaded", "uploading", "processed")

                assertThat(response.obj).isEqualTo("file")
                assertThat(response.filename).isEqualTo(request.file.name)
                assertThat(response.purpose).isEqualTo(request.purpose)
            }

            @BeforeEach
            fun setUp() {
                every {
                    webClient
                        .post()
                        .uri("/files")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .body(any<BodyInserters.MultipartInserter>())
                        .retrieve()
                        .bodyToMono(FileResponse::class.java)
                } returns Mono.just(expected)
            }

            @Test
            @DisplayName("비동기")
            fun async() {
                StepVerifier
                    .create(service.postFileUploadAsync(request))
                    .assertNext(this::validatedFileResponse)
                    .verifyComplete()
            }

            @Test
            @DisplayName("동기")
            fun blocking() {
                val response = service.postFileUpload(request)
                validatedFileResponse(response)
            }
        }

        @Nested
        @DisplayName("실패: 파일이 존재하지 않는 경우")
        inner class NotFoundFile {

            private val request = FileUploadRequest(filename = "noMatch", purpose = "fine-tune")

            @BeforeEach
            fun setUp() {
                every {
                    webClient
                        .post()
                        .uri("/files")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .body(any<BodyInserters.MultipartInserter>())
                } throws WebClientRequestException(
                    NoSuchFileException("noMatch"),
                    HttpMethod.POST,
                    URI.create("/files"),
                    HttpHeaders.EMPTY
                )
            }

            @Test
            @DisplayName("비동기")
            fun async() {
                Assertions.assertThatThrownBy { service.postFileUploadAsync(request) }
                    .isInstanceOf(WebClientRequestException::class.java)
            }

            @Test
            @DisplayName("동기")
            fun blocking() {
                Assertions.assertThatThrownBy { service.postFileUpload(request) }
                    .isInstanceOf(WebClientRequestException::class.java)
            }
        }

        @Nested
        @DisplayName("실패: purpose 를 잘 못 작성한 경우")
        inner class NoMatchPurpose {

            private val request = FileUploadRequest(
                filename = "src/test/resources/openai/test.jsonl",
                purpose = "noMatch"
            )

            @BeforeEach
            fun setUp() {
                every {
                    webClient
                        .post()
                        .uri("/files")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .body(any<BodyInserters.MultipartInserter>())
                        .retrieve()
                        .bodyToMono(FileResponse::class.java)
                } answers {
                    Mono.error(
                        WebClientResponseException.create(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.reasonPhrase,
                            HttpHeaders.EMPTY,
                            byteArrayOf(),
                            StandardCharsets.UTF_8
                        )
                    )
                }
            }

            @Test
            @DisplayName("비동기")
            fun async() {
                StepVerifier
                    .create(service.postFileUploadAsync(request))
                    .expectError(WebClientResponseException.BadRequest::class.java)
                    .verify()
            }

            @Test
            @DisplayName("동기")
            fun blocking() {
                Assertions.assertThatThrownBy { service.postFileUpload(request) }
                    .isInstanceOf(WebClientResponseException.BadRequest::class.java)
                    .hasMessageContaining("400")
            }
        }

        @Nested
        @DisplayName("실패: 파일의 확장자를 지원하지 않는 경우")
        inner class NoMatchExtension {

            private val request = FileUploadRequest(
                filename = "src/test/resources/openai/test.jsonl",
                purpose = "noMatch"
            )

            @BeforeEach
            fun setUp() {
                every {
                    webClient
                        .post()
                        .uri("/files")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .body(any<BodyInserters.MultipartInserter>())
                        .retrieve()
                        .bodyToMono(FileResponse::class.java)
                } answers {
                    Mono.error(
                        WebClientResponseException.create(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.reasonPhrase,
                            HttpHeaders.EMPTY,
                            byteArrayOf(),
                            StandardCharsets.UTF_8
                        )
                    )
                }
            }

            @Test
            @DisplayName("비동기")
            fun async() {
                StepVerifier
                    .create(service.postFileUploadAsync(request))
                    .expectError(WebClientResponseException.BadRequest::class.java)
                    .verify()
            }

            @Test
            @DisplayName("동기")
            fun blocking() {
                Assertions.assertThatThrownBy { service.postFileUpload(request) }
                    .isInstanceOf(WebClientResponseException.BadRequest::class.java)
                    .hasMessageContaining("400")
            }
        }
    }

    @Nested
    @DisplayName("파일 삭제 - DELETE /files/{id}")
    inner class DeleteFile {

        @Nested
        @DisplayName("성공")
        inner class Success {
            private val fileId = "file-m4c88kO1AA9Ekg6hkJlx16uU"

            private val expectedDeleteResponse = FileDeleteResponse(
                id = "file-m4c88kO1AA9Ekg6hkJlx16uU",
                obj = "file",
                deleted = true
            )

            private fun validatedFileDeleteResponse(
                response: FileDeleteResponse
            ) {
                assertThat(response.id).isEqualTo(fileId)
                assertThat(response.obj).isEqualTo("file")
                assertThat(response.deleted).isEqualTo(true)
            }

            @BeforeEach
            fun setUp() {
                every {
                    webClient
                        .delete()
                        .uri("/files/${fileId}")
                        .retrieve()
                        .bodyToMono(FileDeleteResponse::class.java)
                } returns Mono.just(expectedDeleteResponse)
            }

            @Test
            @DisplayName("비동기")
            fun async() {
                StepVerifier
                    .create(service.deleteFileAsync(fileId))
                    .assertNext(this::validatedFileDeleteResponse)
                    .verifyComplete()
            }

            @Test
            @DisplayName("동기")
            fun blocking() {
                val response = service.deleteFile(fileId)
                validatedFileDeleteResponse(response)
            }
        }

        @Nested
        @DisplayName("실패: ID 없음")
        inner class NoMatchID {

            @BeforeEach
            fun setUp() {
                every {
                    webClient
                        .delete()
                        .uri("/files/noMatch")
                        .retrieve()
                        .bodyToMono(FileDeleteResponse::class.java)
                } answers {
                    Mono.error(
                        WebClientResponseException.create(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.reasonPhrase,
                            HttpHeaders.EMPTY,
                            byteArrayOf(),
                            StandardCharsets.UTF_8
                        )
                    )
                }
            }

            @Test
            @DisplayName("비동기")
            fun async() {
                StepVerifier
                    .create(service.deleteFileAsync("noMatch"))
                    .expectError(WebClientResponseException.NotFound::class.java)
                    .verify()
            }

            @Test
            @DisplayName("동기")
            fun blocking() {
                Assertions.assertThatThrownBy { service.deleteFile("noMatch") }
                    .isInstanceOf(WebClientResponseException.NotFound::class.java)
                    .hasMessageContaining("404")
            }
        }
    }
}
