package shrimp.openai_core.utility

import org.reactivestreams.Publisher
import org.springframework.web.reactive.function.client.WebClient

inline fun <reified T, P : Publisher<T>> WebClient.RequestBodySpec.body(
    publisher: P
): WebClient.RequestHeadersSpec<*> {
    return this.body(publisher, T::class.java)
}
