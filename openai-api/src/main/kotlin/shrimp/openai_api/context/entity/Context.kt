package shrimp.openai_api.context.entity

import jakarta.persistence.*
import org.hibernate.annotations.Where
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*

@Entity
@Where(clause = "delete_at IS NULL")
@EntityListeners(AuditingEntityListener::class)
class Context(
    @Id
    @Column(name = "context_id")
    var id: UUID,

    @Column(
        name = "title", nullable = false,
        columnDefinition = "LONGTEXT"
    )
    var title: String,

    @Column(
        name = "description", nullable = true,
        columnDefinition = "LONGTEXT"
    )
    var description: String? = null,

    @Column(name = "model", nullable = false)
    var model: String,

    @Column(name = "delete_at", nullable = true)
    var deleteAt: LocalDateTime? = null
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
}
