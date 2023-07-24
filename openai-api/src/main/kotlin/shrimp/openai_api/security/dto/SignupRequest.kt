package shrimp.openai_api.security.dto

import shrimp.openai_api.security.entity.Account

data class SignupRequest(
    val name: String,
    val email: String,
    val password: String
) {
    fun convertEntity(): Account {
        return Account(
            name = this.name,
            email = this.email,
            password = this.password
        )
    }
}
