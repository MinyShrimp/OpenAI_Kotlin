package shrimp.openai_kotlin.base

import org.springframework.web.reactive.function.client.WebClient

/**
 * OpenAI와 통신을 위한 WebClient 객체를 감싸
 * OpenAI API 만을 위한 WebClient 객체를 반환하는 클래스
 *
 * @author 김회민
 * @since 2023-03-26
 */
class OpenAIClient(private val webClient: WebClient) {
    operator fun invoke(): WebClient {
        return this.webClient
    }
}