package shrimp.openai_api.context.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import shrimp.openai_api.context.types.Name
import shrimp.openai_api.context.types.Role
import java.time.LocalDateTime
import java.util.*

@Entity
@EntityListeners(AuditingEntityListener::class)
class History(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "history_id")
    val id: UUID = UUID.randomUUID(),

    @Enumerated(EnumType.STRING)
    var role: Role,

    @Enumerated(EnumType.STRING)
    var name: Name,

    @Column
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "context_id")
    var context: Context?
) {
    @CreatedDate
    lateinit var createAt: LocalDateTime
        private set

    @LastModifiedDate
    lateinit var updateAt: LocalDateTime
        private set
}