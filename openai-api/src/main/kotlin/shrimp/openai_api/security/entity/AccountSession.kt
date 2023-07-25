package shrimp.openai_api.security.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*

@Entity
@EntityListeners(AuditingEntityListener::class)
class AccountSession(
    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    val account: Account,

    @Column(name = "token", nullable = false, unique = true)
    var token: String
) {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "account_session_id")
    val id: UUID = UUID.randomUUID()

    @CreatedDate
    lateinit var createAt: LocalDateTime
        private set

    @LastModifiedDate
    lateinit var updateAt: LocalDateTime
        private set
}
