package shrimp.openai_api.security.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*

@Entity
@EntityListeners(AuditingEntityListener::class)
class Account(
    @Column(name = "name", nullable = false)
    var name: String,

    @Column(
        name = "email",
        nullable = false,
        unique = true
    )
    var email: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @ElementCollection(fetch = FetchType.EAGER)
    var roles: MutableSet<AccountRole> = mutableSetOf(AccountRole.USER)
) {
    @Id
    @Column(name = "account_id")
    @GeneratedValue(generator = "UUID")
    val id: UUID = UUID.randomUUID()

    @Column(name = "login_at", nullable = true)
    var loginAt: LocalDateTime? = null

    @Column(name = "logout_at", nullable = true)
    var logoutAt: LocalDateTime? = null

    @Column(name = "is_login", nullable = false)
    var isLogin: Boolean = false

    @OneToOne(mappedBy = "account")
    var session: AccountSession? = null

    @CreatedDate
    lateinit var createAt: LocalDateTime
        private set

    @LastModifiedDate
    lateinit var updateAt: LocalDateTime
        private set

    @Column(name = "delete_at", nullable = true)
    var deleteAt: LocalDateTime? = null
}
