package shrimp.openai_core.base

data class OpenAIOption(
    val apiKey: String
) {
    fun getAuthorization(): Pair<String, String> {
        return Pair("Authorization", "Bearer ${this.apiKey}");
    }
}
