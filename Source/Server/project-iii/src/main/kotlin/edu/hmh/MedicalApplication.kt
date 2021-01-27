package edu.hmh

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class MedicalApplication

fun main(args: Array<String>) {
    runApplication<MedicalApplication>(*args)
}

@RestController
class TerminateController {
    @Autowired
    lateinit var appContext: ConfigurableApplicationContext

    @GetMapping("/shut")
    fun shutdownApplication() {
        appContext.registerShutdownHook()
        appContext.close()
    }
}