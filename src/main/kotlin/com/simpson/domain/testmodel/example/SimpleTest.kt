package com.simpson.domain.testmodel.example

import com.simpson.domain.testmodel.TestBase

class SimpleTest(idx: Long,
                 repeatCnt: Int,
                 url: String,
                 dbUrl: String,
                 dbUser: String,
                 dbPassword: String)
    : TestBase(idx, repeatCnt, url, dbUrl, dbUser, dbPassword) {
    override fun toString(): String {
        return "SimpleTest.%d.%d.%s".format(idx, repeatCnt, url)
    }

    override fun getName(): String = "SimpleTest"

    override fun init(): Boolean {
        return true
    }

    override fun execute(num: Int): Boolean {
        Thread.sleep(50)
        print(num)
        return true
    }

    override fun fint(): () -> Unit = { /* do nothing */ }

    override fun reset(): () -> Unit = { /* do nothing */ }
}