package shrimp.openai_kotlin.api.file.request

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.core.io.FileSystemResource
import org.springframework.http.MediaType
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.web.reactive.function.BodyInserters
import java.io.File

/**
 * File Upload Request DTO
 * - POST /files
 *
 * @author 김회민
 * @see <a href="https://platform.openai.com/docs/api-reference/files/upload">Files API Document</a>
 * @since 2023-03-26
 */
class FileUploadRequest private constructor(
    val file: FileInfo,
    val purpose: String
) {
    @JsonIgnore
    val multipartBody: BodyInserters.MultipartInserter

    constructor(
        file: File,
        purpose: String,
        uploadFilename: String? = null
    ) : this(
        FileInfo(file, uploadFilename ?: file.name),
        purpose
    )

    constructor(
        filename: String,
        purpose: String,
        uploadFilename: String? = null
    ) : this(
        FileInfo(filename, uploadFilename ?: filename),
        purpose
    )

    init {
        require(file.name.isNotBlank()) { "File name must not be blank." }
        require(purpose.isNotBlank()) { "Purpose must not be blank." }

        val builder = MultipartBodyBuilder()
        builder.part("purpose", purpose, MediaType.TEXT_PLAIN)
        builder.part("file", file.content, MediaType.APPLICATION_OCTET_STREAM)
            .header(
                "Content-Disposition",
                "form-data; name=\"file\"; filename=\"${file.name}\""
            )
        this.multipartBody = BodyInserters.fromMultipartData(builder.build())
    }

    class FileInfo(
        private val file: File,
        val name: String,
        val content: FileSystemResource = FileSystemResource(file)
    ) {
        constructor(
            filename: String,
            uploadFilename: String
        ) : this(File(filename), uploadFilename)
    }
}
