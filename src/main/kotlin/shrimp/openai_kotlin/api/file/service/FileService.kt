package shrimp.openai_kotlin.api.file.service

import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import shrimp.openai_kotlin.api.file.request.FileUploadRequest
import shrimp.openai_kotlin.api.file.response.FileDeleteResponse
import shrimp.openai_kotlin.api.file.response.FileListResponse
import shrimp.openai_kotlin.api.file.response.FileResponse
import shrimp.openai_kotlin.base.OpenAIClient

/**
 * Files API Service
 *
 * @author 김회민
 * @see <a href="https://platform.openai.com/docs/api-reference/files">Files API Document</a>
 * @since 2023-03-26
 */
@Service
class FileService(
    private val openAIClient: OpenAIClient
) {

    /**
     * GET /files. Async.
     */
    fun getFileListAsync(): Mono<FileListResponse> {
        return openAIClient()
            .get()
            .uri("/files")
            .retrieve()
            .bodyToMono(FileListResponse::class.java)
    }

    /**
     * GET /files. Blocking.
     */
    fun getFileList(): FileListResponse {
        return getFileListAsync().block()!!
    }

    /**
     * GET /files/{id}. Async.
     */
    fun getFileAsync(
        id: String
    ): Mono<FileResponse> {
        return openAIClient()
            .get()
            .uri("/files/$id")
            .retrieve()
            .bodyToMono(FileResponse::class.java)
    }

    /**
     * GET /files/{id}. Blocking.
     */
    fun getFile(
        id: String
    ): FileResponse {
        return getFileAsync(id).block()!!
    }

    /**
     * GET /files/{id}/content. Async.
     */
    fun getFileContentAsync(
        id: String
    ): Mono<String> {
        return openAIClient()
            .get()
            .uri("/files/$id/content")
            .retrieve()
            .bodyToMono(String::class.java)
    }

    /**
     * GET /files/{id}/content. Blocking.
     */
    fun getFileContent(
        id: String
    ): String {
        return getFileContentAsync(id).block()!!
    }

    /**
     * POST /files. Async.
     */
    fun postFileUploadAsync(
        request: FileUploadRequest
    ): Mono<FileResponse> {
        return openAIClient()
            .post()
            .uri("/files")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(request.multipartBody)
            .retrieve()
            .bodyToMono(FileResponse::class.java)
    }

    /**
     * POST /files. Blocking.
     */
    fun postFileUpload(
        request: FileUploadRequest
    ): FileResponse {
        return postFileUploadAsync(request).block()!!
    }

    /**
     * DELETE /files/{id}. Async.
     */
    fun deleteFileAsync(
        id: String
    ): Mono<FileDeleteResponse> {
        return openAIClient()
            .delete()
            .uri("/files/$id")
            .retrieve()
            .bodyToMono(FileDeleteResponse::class.java)
    }

    /**
     * DELETE /files/{id}. Blocking.
     */
    fun deleteFile(
        id: String
    ): FileDeleteResponse {
        return deleteFileAsync(id).block()!!
    }
}
