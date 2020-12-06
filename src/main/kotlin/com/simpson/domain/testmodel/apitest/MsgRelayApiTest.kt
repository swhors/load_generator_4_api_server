package com.simpson.domain.testmodel.apitest

import com.simpson.domain.client.HttpClient
import com.simpson.domain.testmodel.TestBase

class MsgRelayApiTest(idx: Long,
                         repeatCnt: Int,
                         url: String,
                         dbUrl: String,
                         dbUser: String,
                         dbPassword: String)
    : TestBase(idx, repeatCnt, url, dbUrl, dbUser, dbPassword) {
    private lateinit var httpClient: HttpClient
    override fun toString(): String {
        return "UserRegistApiTest.%d.%d.%s".format(idx, repeatCnt, url)
    }

    override fun getName(): String = "UserRegistApiTest"

    override fun init(): Boolean {
        httpClient = HttpClient(url)
        return true
    }

    override fun execute(num: Int): Boolean {
        Thread.sleep(50)
        val debug = false
        var result = true
        if (debug)
            println("$idx,$num")
        else {
            val message = "[Test Message]+ $num"
            result = httpClient.sendPostMessageApiChatMsg(message = message)
        }
        return result
    }

    override fun fint(): () -> Unit = { /* do nothing */ }
    override fun reset(): () -> Unit = { /* do nothing */ }
}