package com.simpson.domain.util

import org.slf4j.LoggerFactory

open class Logger {
    val logger = LoggerFactory.getLogger(this.javaClass)
}