package com.simpson.domain.client

import java.sql.Connection
import java.sql.DriverManager
import java.util.*

class TestDBConnector(val url: String, val user: String, val passwd: String) {
    val properties = Properties()
    lateinit var listOfGID : ArrayList<Long>

    init {
        with(properties) {
            put("user", user)
            put("password", passwd)
        }
    }

    fun deleteAllTestData() {
        DriverManager.getConnection(url, properties).use {
                connection ->
                    getGroupIdxs(connection)
                    deleteAllAccount(connection)
                    deleteAllGroups(connection)
        }
    }

    private fun getGroupIdxs(connection: Connection) {
        this.listOfGID
        val query = "select gid from apitestdb.group where uid in " +
                (select uid from apitestdb.account where umail like 'abcd%')"
        val rs = connection.createStatement().executeQuery(query)
        while (rs.next()) {
            this.listOfGID.add(rs.getLong("gid"))
        }
    }

    private fun deleteAllGroups(connection: Connection) {
        if (this.listOfGID.size > 0) {
            val quries = listOf<String>(
                "set foreign_key_checks = 0",
                "set SQL_SAFE_UPDATES = 0",
                "delete from apitestdb.group where gid in ("
                        + this.listOfGID.joinToString(",")
                        + ")",
                "set SQL_SAFE_UPDATES = 1",
                "set foreign_key_checks = 1"
            )
            val statement = connection.createStatement()
            for (query:String in quries) {
                with(statement) {
                    execute(query)
                }
            }
        }
    }

    private fun deleteAllAccount(connection: Connection) {
        val quries = listOf<String>(
            "set foreign_key_checks = 0;",
            "set SQL_SAFE_UPDATES = 0;",
            "delete from apitestdb.group where umail like \'abcd%\';",
            "set SQL_SAFE_UPDATES = 1;",
            "set foreign_key_checks = 1"
        )
        val statement = connection.createStatement()
        for (query:String in quries) {
            with(statement) {
                execute(query)
            }
        }
    }
}