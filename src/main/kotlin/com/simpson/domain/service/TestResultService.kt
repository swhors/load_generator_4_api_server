package com.simpson.domain.service

import com.simpson.domain.model.TestResult
import org.springframework.stereotype.Service

@Service
interface TestResultService {
    fun getAllResults(): List<TestResult>?
    fun getResult(id: Int): TestResult?
    fun saveResult(testResult: TestResult): TestResult
    fun getResultByName(name: String):TestResult?
}