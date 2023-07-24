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

//    override fun addViewControllers(
//        registry: ViewControllerRegistry
//    ) {
//        registry.addViewController("/")
//        registry.setOrder(Ordered.HIGHEST_PRECEDENCE)
//    }
//
//    override fun addResourceHandlers(
//        registry: ResourceHandlerRegistry
//    ) {
//        registry.addResourceHandler("/**")
//            .addResourceLocations(
//                "classpath:/static/", "classpath:/public/", "classpath:/",
//                "classpath:/resources/", "classpath:/META-INF/resources/", "classpath:/META-INF/resources/webjars/"
//            )
//    }
}
