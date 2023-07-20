package shrimp.openai_api.openai.token_calculate.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import shrimp.openai_api.openai.token_calculate.dto.request.GetContentTokenSizeRequest
import shrimp.openai_api.openai.token_calculate.dto.request.GetRequestTokenSizeRequest
import shrimp.openai_core.utility.TokenCalculatorService

@RestController
@RequestMapping("/api/calculate/token")
class TokenCalculateController(
    private val tokenCalculatorService: TokenCalculatorService
) {

    @PostMapping("/request")
    fun getRequestTokenSize(
        @RequestBody prompt: GetRequestTokenSizeRequest
    ): List<Int> {
        return tokenCalculatorService.getRequestTokenSize(
            prompt.model,
            prompt.messages
        )
    }

    @PostMapping("/content")
    fun getContentTokenSize(
        @RequestBody prompt: GetContentTokenSizeRequest
    ): Int {
        return tokenCalculatorService.getTokenSize(
            prompt.model,
            prompt.content
        )
    }
}
