package com.simpson.domain.controller

import com.simpson.domain.task.Executor
import com.simpson.domain.model.TestResult
import com.simpson.domain.service.TestResultService
import com.simpson.domain.testmodel.TestBase
import com.simpson.domain.testmodel.TestInterface
import com.simpson.domain.testmodel.apitest.MsgRelayApiTest
import com.simpson.domain.testmodel.apitest.UserRegistApiTest
import com.simpson.domain.testmodel.example.SimpleTest
import com.simpson.domain.testmodel.exception.TestModelException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory

@Slf4j
@RestController // @Controller + @ResponseBody
@RequestMapping//("/template")

class MainViewController {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Value("\${run.basic.first-api-url}")
    var sdTargetUrl: String = "localhost"

    @Value("\${run.basic.second-api-url}")
    var authTargetUrl: String = "localhost"

    @Value("\${run.basic.test-count.limit}")
    var testLimit: Int = 100000

    @Value("\${spring.datasource.url}")
    var dbUrl: String = "localhost:3306"

    @Value("\${spring.datasource.username}")
    var dbUser: String = "simpson"

    @Value("\${spring.datasource.password}")
    var dbPassword: String = "simpson"

    @Value("\${run.testdb.url")
    var testDbUrl: String = "localhost:3306"

    @Value("\${run.testdb.username")
    var testDbUser: String = "simpson"

    @Value("\${run.testdb.password")
    var testDbPasswd: String = "simpson"

    @Autowired
    private lateinit var testResultService : TestResultService

    @GetMapping("/api/data/results")
    private fun getTemplates(): ResponseEntity<Any> {
        return ResponseEntity
            .ok().body(testResultService.getAllResults())
    }

    @GetMapping("/api/data/result/{id}")
    private fun getTemplateById(@PathVariable id: Int): ResponseEntity<Any> {
        return ResponseEntity
            .ok().body(testResultService.getResult(id))
    }

    @GetMapping("/api/data/result")
    private fun getTemplateByName(@RequestParam(value = "name") name: String): ResponseEntity<Any?> {
        return ResponseEntity
            .ok().body(testResultService.getResultByName(name))
    }

    @PostMapping("/api/data/result")
    private fun postTemplate(@RequestBody templateModel: TestResult): ResponseEntity<Any> {
        testResultService.saveResult(templateModel)
        return ResponseEntity
            .ok().body(true)
    }

    //    val testModel : Map<String, Any> = mapOf("example" to Example())
    private fun name2Model(
        name: String,
        idx: Long,
        repeatCnt: Int
    ) : TestBase {
        return when(name) {
            "SimpleTest" -> SimpleTest(idx, repeatCnt, "localhost:8080",
                                        testDbUrl, testDbUser, testDbPasswd)
            "UserRegistApiTest" -> UserRegistApiTest(idx, repeatCnt, authTargetUrl,
                                        testDbUrl, testDbUser, testDbPasswd)
            "MsgRelayApiTest" -> MsgRelayApiTest(idx, repeatCnt, sdTargetUrl,
                                        testDbUrl, testDbUser, testDbPasswd)
            else -> throw TestModelException("Not Found")
        }
    }

    @GetMapping("/api/generate/reset/{test_model_name}")
    private fun resetGenData(@PathVariable testModelName: String) : String {
        val testModel: TestInterface = name2Model(testModelName, 0, 0)
        when (testModel.init()) {
            true -> {
                testModel.reset()
                testModel.fint()
                return "success"
            }
            false -> {
                logger.error("error : resetGenData, fail")
                return "error : resetGenData, fail"
            }
        }
    }

    @Throws(TestModelException::class)
    @GetMapping("/api/generate/{thread_count}/{start_idx}/{repeat_cnt}/{test_model_name}")
    private fun genWithCountAndIdx(
        @PathVariable("thread_count") threadCount: Int,
        @PathVariable("start_idx") startIdx: Long,
        @PathVariable("repeat_cnt") repeatCnt: Int,
        @PathVariable("test_model_name") testModelName: String
    ): String {
        var i = threadCount
        var idx = startIdx
        while( i > 0 ) {
            val testModel = name2Model(
                testModelName,
                idx = idx,
                repeatCnt = repeatCnt
            )
            val executor = Thread(
                Executor(
                    idx++,
                    i,
                    repeatCnt,
                    testModel,
                    dbUrl,
                    dbUser,
                    dbPassword
                )
            )
            executor.start()
            i--
            idx++
        }
        return "Hello"
    }
}