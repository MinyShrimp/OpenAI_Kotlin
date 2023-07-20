package shrimp.openai_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaAuditing
@EnableJpaRepositories
@SpringBootApplication
class MainApplication

fun main(args: Array<String>) {
    runApplication<MainApplication>(*args)
}
