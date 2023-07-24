package shrimp.openai_api.configure

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebSecurityConfig {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    fun userSecurityFilterChain(
        http: HttpSecurity
    ): SecurityFilterChain {
        http {
            authorizeRequests {
                authorize("/api/**", hasAuthority("ROLE_USER"))
            }
            httpBasic { }
            csrf { disable() }
        }

        return http.build()
    }

    @Bean
    fun basicSecurityFilterChain(
        http: HttpSecurity
    ): SecurityFilterChain {
        http {
            authorizeRequests {
                authorize("/auth/**", permitAll)
            }
            httpBasic { }
            csrf { disable() }
        }

        return http.build()
    }
}
