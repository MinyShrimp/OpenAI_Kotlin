package shrimp.openai_core.base

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.retry.support.RetryTemplate
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClientException

@Aspect
@Component
class WebClientAspect(
    private val retryTemplate: RetryTemplate
) {
    @Around(
        "execution(* org.springframework.web.reactive.function.client.DefaultWebClient.methodInternal(..))"
    )
    fun aroundWebClientUtils(
        joinPoint: ProceedingJoinPoint
    ): Any {
        return retryTemplate.execute<Any, WebClientException> {
            joinPoint.proceed()
        }
    }
}
