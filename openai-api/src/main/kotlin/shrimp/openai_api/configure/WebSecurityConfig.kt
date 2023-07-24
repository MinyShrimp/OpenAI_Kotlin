package shrimp.openai_api.configure

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebSecurityConfig {
    @Bean
    open fun filterChain(
        http: HttpSecurity
    ): SecurityFilterChain {
        http {
            securityMatcher("/api/**")
            authorizeRequests {
                authorize(anyRequest, permitAll)
            }
            formLogin { }
            httpBasic { }
            csrf {
                disable()
            }
        }

        return http.build()
    }
}
