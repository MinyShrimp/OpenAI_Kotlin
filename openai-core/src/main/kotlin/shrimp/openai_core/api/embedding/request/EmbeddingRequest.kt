package shrimp.openai_core.api.embedding.request

import com.fasterxml.jackson.annotation.JsonValue

/**
 * Embeddings 요청 객체
 *
 * @author 김회민
 * @see <a href="https://platform.openai.com/docs/api-reference/embeddings">API Document</a>
 * @since 2023-03-26
 */
class EmbeddingRequest(
    val model: Model = Model.EMBEDDING_ADA_002,
    val input: List<String>
) {

    enum class Model(
        @JsonValue val value: String
    ) {
        EMBEDDING_ADA_002("text-embedding-ada-002"),

        SIMILARITY_ADA_001("text-similarity-ada-001"),
        SIMILARITY_BABBAGE_001("text-similarity-babbage-001"),
        SIMILARITY_CURIE_001("text-similarity-curie-001"),
        SIMILARITY_DAVINCI_001("text-similarity-davinci-001"),

        SEARCH_ADA_DOC_001("text-search-ada-doc-001"),
        SEARCH_ADA_QUERY_001("text-search-ada-query-001"),
        SEARCH_BABBAGE_DOC_001("text-search-babbage-doc-001"),
        SEARCH_BABBAGE_QUERY_001("text-search-babbage-query-001"),
        SEARCH_CURIE_DOC_001("text-search-curie-doc-001"),
        SEARCH_CURIE_QUERY_001("text-search-curie-query-001"),
        SEARCH_DAVINCI_DOC_001("text-search-davinci-doc-001"),
        SEARCH_DAVINCI_QUERY_001("text-search-davinci-query-001"),

        CODE_SEARCH_ADA_CODE_001("code-search-ada-code-001"),
        CODE_SEARCH_ADA_TEXT_001("code-search-ada-text-001"),
        CODE_SEARCH_BABBAGE_CODE_001("code-search-babbage-code-001"),
        CODE_SEARCH_BABBAGE_TEXT_001("code-search-babbage-text-001");
    }
}
