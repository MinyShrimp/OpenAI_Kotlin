package shrimp.openai_api.security.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import shrimp.openai_api.security.entity.Account

data class SignupRequest(
    @field:NotBlank(message = "이름을 입력해주세요.")
    val name: String,

    @field:Email(message = "이메일 형식이 아닙니다.")
    @field:NotBlank(message = "이메일을 입력해주세요.")
    val email: String,

    @field:NotBlank(message = "비밀번호를 입력해주세요.")
    @field:Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*[!@#\$%^*+=-])(?=.*[0-9]).{8,}\$",
        message = "비밀번호는 영문, 숫자, 특수문자를 포함한 8자리 이상이어야 합니다."
    )
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
