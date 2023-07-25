package shrimp.openai_api.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import shrimp.openai_api.security.dto.ErrorResponse
import shrimp.openai_api.security.exception.AuthorizationException

class ExceptionHandlerFilter(
    private val objectMapper: ObjectMapper
) : Filter {
    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        val resp = response as HttpServletResponse

        try {
            chain.doFilter(request, response)
        } catch (e: AuthorizationException) {
            resp.status = HttpStatus.UNAUTHORIZED.value()
            resp.contentType = "application/json; charset=utf-8"

            val errorResp = ErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.message ?: "Unauthorized")
            resp.writer.write(objectMapper.writeValueAsString(errorResp))
        }
    }
}
