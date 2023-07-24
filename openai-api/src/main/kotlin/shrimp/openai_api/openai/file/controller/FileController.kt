package shrimp.openai_api.openai.file.controller

import mu.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import shrimp.openai_core.api.file.response.FileResponse
import shrimp.openai_core.api.file.service.FileService
import shrimp.openai_core.base.OpenAIOption

@RestController
@RequestMapping("/api/file")
class FileController(
    private val fileService: FileService
) {
    private val logger = KotlinLogging.logger {}

    @GetMapping
    fun getFileList(
        @RequestHeader("Authorization") auth: String,
    ): List<FileResponse> {
        logger.info { auth }
        
        val resp = fileService.getFileList(
            OpenAIOption(apiKey = auth.replace("Bearer ", ""))
        )

        return resp.data ?: throw Exception("No data")
    }
}