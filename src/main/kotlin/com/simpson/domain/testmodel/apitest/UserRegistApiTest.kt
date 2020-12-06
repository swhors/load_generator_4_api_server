package com.simpson.domain.testmodel.apitest

import com.simpson.domain.client.HttpClient
import com.simpson.domain.client.TestDBConnector
import com.simpson.domain.testmodel.TestBase
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory

@Slf4j
class UserRegistApiTest(idx: Long,
                          repeatCnt: Int,
                          url: String,
                          dbUrl: String,
                          dbUser: String,
                          dbPassword: String)
    : TestBase(idx, repeatCnt, url, dbUrl, dbUser, dbPassword) {
    private lateinit var httpClient: HttpClient
    private lateinit var testDBConnector: TestDBConnector
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun toString(): String {
        return "UserRegistApiTest.%d.%d.%s".format(idx, repeatCnt, url)
    }

    override fun getName(): String = "UserRegistApiTest"

    override fun init(): Boolean {
        httpClient = HttpClient(url)
        testDBConnector = TestDBConnector(dbUrl, dbUser, dbPassword)
        return true
    }

    private val umailPrefix = "abcd"
    private val umailTail = "@test.com"
    private val devIdPrefix = "t"
    private val devIdTail = "t"
    private val phoneNoPrefix = "011"

    override fun execute(num: Int): Boolean {
        Thread.sleep(50)
        val debug = false
        var result = true
        if (debug)
            logger.info("$idx,$num")
        else {
            result = httpClient.sendPostRegistUserApi(num = num,
                umail = umailPrefix + num + umailTail,
                deviceId = devIdPrefix + num + devIdTail,
                phoneNo = phoneNoPrefix + num)
        }
        return result
    }

    override fun fint(): () -> Unit = { /* do nothing */ }

    override fun reset(): () -> Unit = {
        try {
            testDBConnector.deleteAllTestData()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}