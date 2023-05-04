package shrimp.openai_kotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OpenaiKotlinApplication

fun main(args: Array<String>) {
    runApplication<OpenaiKotlinApplication>(*args)
}
