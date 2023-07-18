package shrimp.openai_core.base

import org.springframework.web.reactive.function.client.WebClient

/**
 * OpenAI와 통신을 위한 WebClient 객체를 감싸
 * OpenAI API 만을 위한 WebClient 객체를 반환하는 클래스
 *
 * @author 김회민
 * @since 2023-03-26
 */
class OpenAIClient(
    private val webClient: WebClient
) {
    operator fun invoke(
        option: OpenAIOption? = null
    ): WebClient {
        if (option == null) {
            return this.webClient
        }

        val authPair = option.getAuthorization()
        return this.webClient.mutate()
            .defaultHeader(authPair.first, authPair.second)
            .build()
    }
}
