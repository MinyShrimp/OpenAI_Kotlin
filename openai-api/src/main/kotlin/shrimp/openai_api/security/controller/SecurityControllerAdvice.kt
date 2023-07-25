package shrimp.openai_api.security.controller

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import shrimp.openai_api.security.dto.ErrorResponse
import shrimp.openai_api.security.exception.AuthenticationException

@RestControllerAdvice
class SecurityControllerAdvice {
    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthorizationException(
        e: AuthenticationException
    ): ResponseEntity<ErrorResponse> {
        logger.error { e.stackTraceToString() }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponse(
                    status = HttpStatus.BAD_REQUEST.value(),
                    message = e.message ?: "Bad Request"
                )
            )
    }
}
