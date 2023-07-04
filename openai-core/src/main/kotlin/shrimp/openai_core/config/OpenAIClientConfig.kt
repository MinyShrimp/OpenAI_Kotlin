package shrimp.openai_core.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import shrimp.openai_core.base.OpenAIClient
import shrimp.openai_core.utils.getApiKey

/**
 * OpenAI와 통신을 위한 WebClient 객체의 기본 설정
 *
 * @author 김회민
 * @since 2023-03-25
 */
@Configuration
@ComponentScan(basePackages = ["shrimp.openai_core"])
open class OpenAIClientConfig(
    private val env: Environment,
    private val mapper: ObjectMapper
) {
    companion object {
        const val BASE_URL: String = "https://api.openai.com/v1"
    }

    /**
     * ObjectMapper 기본 설정
     * <li>필드의 값이 null, isEmpty 인 경우 Json 문자열에 포함시키지 않음.</li>
     * <li>객체 <-> Json 문자열 = camelCase <-> snake_case</li>
     */
    @Bean
    open fun exchangeStrategy(): ExchangeStrategies {
        mapper.run {
            setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
        }

        return ExchangeStrategies.builder()
            .codecs { configurer ->
                configurer
                    .defaultCodecs()
                    .jackson2JsonEncoder(Jackson2JsonEncoder(mapper))
                configurer
                    .defaultCodecs()
                    .jackson2JsonDecoder(Jackson2JsonDecoder(mapper))
                configurer
                    .defaultCodecs()
                    .maxInMemorySize(-1)
            }.build()
    }

    /**
     * WebClient 기본 설정
     * <li>Authorization: Bearer 'OPENAI_API_KEY'</li>
     * <li>Content-Type: application/json</li>
     * <li>Base-URL: https://api.openai.com/v1</li>
     */
    @Bean
    open fun openAIClient(
        exchangeStrategy: ExchangeStrategies
    ): OpenAIClient {
        return OpenAIClient(
            WebClient
                .builder()
                .exchangeStrategies(exchangeStrategy)
                .defaultHeader(HttpHeaders.AUTHORIZATION, getAuthToken())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl(BASE_URL)
                .build()
        )
    }

    /**
     * Authorization 헤더 값 설정
     */
    private fun getAuthToken(): String {
        return "Bearer " + getApiKey(env)
    }
}
