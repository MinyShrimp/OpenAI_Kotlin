package shrimp.openai_core.api.file.service

import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import shrimp.openai_core.api.file.request.FileUploadRequest
import shrimp.openai_core.api.file.response.FileDeleteResponse
import shrimp.openai_core.api.file.response.FileListResponse
import shrimp.openai_core.api.file.response.FileResponse
import shrimp.openai_core.base.OpenAIClient
import shrimp.openai_core.base.OpenAIOption

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
    fun getFileListAsync(
        option: OpenAIOption? = null
    ): Mono<FileListResponse> {
        return openAIClient(option)
            .get()
            .uri("/files")
            .retrieve()
            .bodyToMono(FileListResponse::class.java)
    }

    /**
     * GET /files. Blocking.
     */
    fun getFileList(
        option: OpenAIOption? = null
    ): FileListResponse {
        return getFileListAsync(option).block()!!
    }

    /**
     * GET /files/{id}. Async.
     */
    fun getFileAsync(
        id: String,
        option: OpenAIOption? = null
    ): Mono<FileResponse> {
        return openAIClient(option)
            .get()
            .uri("/files/$id")
            .retrieve()
            .bodyToMono(FileResponse::class.java)
    }

    /**
     * GET /files/{id}. Blocking.
     */
    fun getFile(
        id: String,
        option: OpenAIOption? = null
    ): FileResponse {
        return getFileAsync(id, option).block()!!
    }

    /**
     * GET /files/{id}/content. Async.
     */
    fun getFileContentAsync(
        id: String,
        option: OpenAIOption? = null
    ): Mono<String> {
        return openAIClient(option)
            .get()
            .uri("/files/$id/content")
            .retrieve()
            .bodyToMono(String::class.java)
    }

    /**
     * GET /files/{id}/content. Blocking.
     */
    fun getFileContent(
        id: String,
        option: OpenAIOption? = null
    ): String {
        return getFileContentAsync(id, option).block()!!
    }

    /**
     * POST /files. Async.
     */
    fun postFileUploadAsync(
        request: FileUploadRequest,
        option: OpenAIOption? = null
    ): Mono<FileResponse> {
        return openAIClient(option)
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
        request: FileUploadRequest,
        option: OpenAIOption? = null
    ): FileResponse {
        return postFileUploadAsync(request, option).block()!!
    }

    /**
     * DELETE /files/{id}. Async.
     */
    fun deleteFileAsync(
        id: String,
        option: OpenAIOption? = null
    ): Mono<FileDeleteResponse> {
        return openAIClient(option)
            .delete()
            .uri("/files/$id")
            .retrieve()
            .bodyToMono(FileDeleteResponse::class.java)
    }

    /**
     * DELETE /files/{id}. Blocking.
     */
    fun deleteFile(
        id: String,
        option: OpenAIOption? = null
    ): FileDeleteResponse {
        return deleteFileAsync(id, option).block()!!
    }
}
