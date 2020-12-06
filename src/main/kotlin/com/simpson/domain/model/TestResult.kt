package com.simpson.domain.model

import java.sql.Date
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class TestResult(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    @GeneratedValue
    val created: Date,
    val testName: String,
    val url: String,
    val started: Long?,
    val stopped: Long?,
    val total: Int?,
    val success: Int?,
    val fail: Int?)