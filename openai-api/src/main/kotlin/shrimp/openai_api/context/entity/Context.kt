package shrimp.openai_api.context.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*

@Entity
@EntityListeners(AuditingEntityListener::class)
class Context(
    @Id
    @Column(name = "context_id")
    var id: UUID,
    var title: String,
    var description: String,
) {
    @OneToMany(
        mappedBy = "context",
        cascade = [CascadeType.ALL]
    )
    var prePromptList: List<Prompt> = listOf()

    @OneToMany(
        mappedBy = "context",
        cascade = [CascadeType.ALL]
    )
    var historyList: List<History> = listOf()

    @CreatedDate
    lateinit var createAt: LocalDateTime
        private set

    @LastModifiedDate
    lateinit var updateAt: LocalDateTime
        private set
}
