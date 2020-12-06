package com.simpson.domain.client

import com.simpson.domain.model.Result
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

class JDBCConnector constructor(private val url: String,
                                private val user: String,
                                private val passwd: String){
    private val SCHEMA = "apitestdb"
    private val TABLE = "test_result"
    fun insertRow(connection: Connection, testResult: Result) {
        try {
            println("Start=" + testResult.started.toString() + ",Stop=" + testResult.stopped.toString())
            val query1 = "INSERT INTO apitestdb.test_result( test_name, url, started, stopped, success, fail, total)" +
                        "       VALUES( \"%s\", \"%s\", %d, %d, %d, %d, %d);"
                            .format(testResult.testName,
                                testResult.url,
                                if (testResult.started!=null) testResult.started else 0L,
                                if (testResult.stopped!=null) testResult.stopped else 0L,
                                testResult.success,
                                testResult.fail,
                                testResult.total);
            with(connection) {
                createStatement().execute(query1)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun insertResult(result: Result) {
        val properties = Properties()
        with(properties) {
            put("user", user)
            put("password", passwd)
        }
        DriverManager.getConnection(url, properties)
            .use {connection -> insertRow(connection, result) }
    }
}