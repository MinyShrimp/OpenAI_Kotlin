package shrimp.openai_api.security.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.util.*

@Entity
class User(
    @Id
    @GeneratedValue(generator = "UUID")
    val id: UUID,
    val name: String,
    val email: String,
    val password: String,
) {
}
