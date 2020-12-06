package com.simpson

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class APILoadTesterApp

fun main(args: Array<String>) {
    runApplication<APILoadTesterApp>(*args)
}