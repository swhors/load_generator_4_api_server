package com.simpson.domain.service

import com.simpson.domain.model.TestResult
import com.simpson.domain.repository.TestResultRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class TestResultSrvImpl constructor(@Autowired private val testResultRepo: TestResultRepo) : TestResultService {
    override fun getAllResults(): List<TestResult>? =
        testResultRepo.findAllBy()

    override fun getResult(id: Int): TestResult? =
        testResultRepo.findById(id).orElse(null)

    @Transactional
    override fun saveResult(testResult: TestResult): TestResult {
        return testResultRepo.save(testResult)
    }

    override fun getResultByName(name: String): TestResult? {
        return testResultRepo.findByTestName(name)
    }
}