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
    var role: Role,

    @Enumerated(EnumType.STRING)
    var name: Name,

    @Column
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "context_id")
    var context: Context
)
