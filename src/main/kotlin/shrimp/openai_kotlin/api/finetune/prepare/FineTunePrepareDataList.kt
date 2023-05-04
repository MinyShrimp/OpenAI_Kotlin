package shrimp.openai_kotlin.api.finetune.prepare

import java.util.stream.Stream

/**
 * 학습 데이터 List 를 편리하게 생성할 수 있도록 도와주는 객체
 *
 * @author 김회민
 * @since 2023-03-27
 */
class FineTunePrepareDataList {
    val dataList: MutableList<FineTunePrepareData> = ArrayList()

    val dataStream: Stream<FineTunePrepareData>
        get() = dataList.stream()

    fun addData(
        prompt: String, completion: String
    ): FineTunePrepareDataList {
        dataList.add(FineTunePrepareData(prompt, completion))
        return this
    }
}
