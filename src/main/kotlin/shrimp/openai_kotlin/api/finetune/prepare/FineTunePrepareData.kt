package shrimp.openai_kotlin.api.finetune.prepare

/**
 * Fine-Tuning 학습 데이터 객체
 *
 * @author 김회민
 * @since 2023-03-27
 */
class FineTunePrepareData(
    prompt: String, completion: String
) {
    companion object {
        private const val PROMPT_SUFFIX = "###\n\n###"
        private const val COMPLETION_PREFIX = " "
    }

    val prompt: String
    val completion: String

    init {
        this.prompt = prompt + PROMPT_SUFFIX
        this.completion = COMPLETION_PREFIX + completion
    }
}
