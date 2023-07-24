package shrimp.openai_api.security.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Collectors

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
    var id: UUID = UUID.randomUUID()
        private set

    @CreatedDate
    lateinit var createAt: LocalDateTime
        private set

    @LastModifiedDate
    lateinit var updateAt: LocalDateTime

    fun getAuthorities(): User {
        return User(
            this.email,
            this.password,
            this.roles.stream()
                .map { role -> SimpleGrantedAuthority("ROLE_$role") }
                .collect(Collectors.toSet())
        )
    }
}
