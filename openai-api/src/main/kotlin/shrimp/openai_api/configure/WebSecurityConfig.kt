package shrimp.openai_api.configure

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import shrimp.openai_api.security.filter.AuthTokenFilter
import shrimp.openai_api.security.filter.ExceptionHandlerFilter
import shrimp.openai_api.security.service.AccountSessionService

@Configuration
class WebSecurityConfig : WebMvcConfigurer {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    fun exceptionFilter(
        objectMapper: ObjectMapper
    ): FilterRegistrationBean<ExceptionHandlerFilter> {
        val registration = FilterRegistrationBean(
            ExceptionHandlerFilter(objectMapper)
        )
        registration.order = 1
        registration.addUrlPatterns("/api/*", "/auth/logout")
        return registration
    }

    @Bean
    fun authFilter(
        accountSessionService: AccountSessionService
    ): FilterRegistrationBean<AuthTokenFilter> {
        val registration = FilterRegistrationBean(
            AuthTokenFilter(accountSessionService)
        )
        registration.order = 2
        registration.addUrlPatterns("/api/*", "/auth/logout")
        return registration
    }
}
