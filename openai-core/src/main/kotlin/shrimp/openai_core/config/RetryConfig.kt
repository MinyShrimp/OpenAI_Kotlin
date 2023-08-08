package shrimp.openai_core.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.retry.annotation.EnableRetry
import org.springframework.retry.backoff.FixedBackOffPolicy
import org.springframework.retry.policy.SimpleRetryPolicy
import org.springframework.retry.support.RetryTemplate

@EnableRetry
@Configuration
class RetryConfig {
    @Bean
    fun retryTemplate(): RetryTemplate {
        val backoff = FixedBackOffPolicy().apply { backOffPeriod = 5000L }
        val retryPolicy = SimpleRetryPolicy(10)

        return RetryTemplate().apply {
            setBackOffPolicy(backoff)
            setRetryPolicy(retryPolicy)
        }
    }
}
