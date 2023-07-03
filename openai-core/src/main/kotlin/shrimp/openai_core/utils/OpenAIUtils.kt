package shrimp.openai_core.utils

import org.springframework.core.env.Environment

fun getApiKey(
    env: Environment
): String {
    return env.getProperty("OPENAI_API_KEY")
        ?: throw SecurityException("OPENAI_API_KEY is not set")
}
