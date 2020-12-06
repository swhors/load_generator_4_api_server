package com.simpson.domain.testmodel

abstract class TestBase(val idx: Long,
                        val repeatCnt: Int,
                        val url: String,
                        val dbUrl: String,
                        val dbUser: String,
                        val dbPassword: String): TestInterface{
    override fun toString(): String {
        return "TestModel.%d.%d".format(idx,repeatCnt)
    }
    abstract fun getName(): String
}