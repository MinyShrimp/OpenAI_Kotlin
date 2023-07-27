package shrimp.openai_api.security.dto.error

enum class ErrorCode(
    val message: String
) {
    None(""),
    ExistEmail("이미 존재하는 이메일입니다."),
    NoExistEmail("존재하지 않는 이메일입니다."),
    NoMatchPassword("비밀번호가 일치하지 않습니다."),
    NoExistCookie("쿠키가 누락되었습니다."),

    AlreadyLogin("이미 로그인 되었습니다."),
    AlreadyLogout("이미 로그아웃 되었습니다."),
}
