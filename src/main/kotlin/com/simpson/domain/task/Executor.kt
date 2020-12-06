package com.simpson.domain.task

import com.simpson.domain.client.JDBCConnector
import com.simpson.domain.model.Result
import com.simpson.domain.testmodel.TestBase
import java.util.*

class Executor constructor(private val idx: Long,
                           private val threadCount: Int,
                           private val repeatCnt: Int,
                           private val testModel: TestBase,
                           private val dbUrl: String,
                           private val dbUser: String,
                           private val dbPasswd: String
) : Runnable {
    override fun run() {
        val msg: String = "\n${Thread.currentThread()} has run.[%10d:%10d]".format(idx, threadCount)
        println(msg)
        val startTime: Long = Date().time
        var successCnt = 0
        var failCnt = 0
        if (testModel.init()) {
            var cnt = 0
            while (cnt < repeatCnt) {
                when(testModel.execute(((threadCount * 1000000) + cnt++))) {
                    true -> successCnt++
                    false -> failCnt++
                }
            }
            testModel.fint()
        }
        val stopTime: Long = Date().time

        val result = Result.Builder(testModel.getName()).fail(failCnt).success(successCnt).started(startTime)
            .stopped(stopTime).url(testModel.url).total(repeatCnt).build()

        val dbCon = JDBCConnector(dbUrl, dbUser, dbPasswd)
        dbCon.insertResult(result)

        val stopMsg: String = "\n${Thread.currentThread()} has stopped.[%10d:%10d:%d:%d]"
            .format(idx, threadCount, successCnt, failCnt)
        println(stopMsg)
    }
}