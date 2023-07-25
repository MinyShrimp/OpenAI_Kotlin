package shrimp.openai_api.configure

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfigure : WebMvcConfigurer {
    override fun addCorsMappings(
        registry: CorsRegistry
    ) {
        registry
            .addMapping("/**")
            .allowedOrigins("http://localhost:3000")
            .allowedHeaders("*")
            .allowedMethods("*")
            .allowCredentials(true)
    }
}
