package com.simpson.domain.repository

import com.simpson.domain.model.TestResult
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TestResultRepo: JpaRepository<TestResult, Int> {
    fun findByTestName(testName: String): TestResult?
    fun findAllBy(): List<TestResult>?
}