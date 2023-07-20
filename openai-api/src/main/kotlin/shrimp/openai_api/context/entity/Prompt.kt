package shrimp.openai_api.context.entity

import jakarta.persistence.*
import shrimp.openai_api.context.types.Name
import shrimp.openai_api.context.types.Role
import java.util.*

@Entity
class Prompt(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "prompt_id")
    val id: UUID = UUID.randomUUID(),

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: Role,

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    var name: Name,

    @Column(name = "content", nullable = false)
    var content: String,

    @Column(name = "ordering", nullable = false)
    var order: Int,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "context_id")
    var context: Context?
)
