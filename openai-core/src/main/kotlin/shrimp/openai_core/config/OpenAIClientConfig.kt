package shrimp.openai_core.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.*
import org.springframework.core.env.Environment
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import shrimp.openai_core.base.OpenAIClient

/**
 * OpenAI와 통신을 위한 WebClient 객체의 기본 설정
 *
 * @author 김회민
 * @since 2023-03-25
 */
@Configuration
@ComponentScan(basePackages = ["shrimp.openai_core"])
open class OpenAIClientConfig(
    private val env: Environment
) {
    companion object {
        const val BASE_URL: String = "https://api.openai.com/v1"
    }

    private fun getApiKey(): String {
        return env.getProperty("OPENAI_API_KEY") ?: ""
    }

    /**
     * ObjectMapper 기본 설정
     * <li>필드의 값이 null 인 경우 Json 문자열에 포함시키지 않음.</li>
     * <li>객체 <-> Json 문자열 = camelCase <-> snake_case</li>
     */
    @Bean
    open fun jsonCustomizer(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer { builder ->
            builder
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
        }
    }

    @Bean
    @DependsOn("jsonCustomizer")
    open fun exchangeStrategies(
        objectMapper: ObjectMapper
    ): ExchangeStrategies {
        return ExchangeStrategies.builder()
            .codecs { configurer ->
                configurer
                    .defaultCodecs()
                    .jackson2JsonEncoder(Jackson2JsonEncoder(objectMapper))
                configurer
                    .defaultCodecs()
                    .jackson2JsonDecoder(Jackson2JsonDecoder(objectMapper))
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
    @DependsOn("exchangeStrategies")
    open fun openAIClient(
        exchangeStrategies: ExchangeStrategies
    ): OpenAIClient {
        return OpenAIClient(
            WebClient
                .builder()
                .exchangeStrategies(exchangeStrategies)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer ${getApiKey()}")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl(BASE_URL)
                .build()
        )
    }
}
