package com.simpson.domain.model

class Result (val testName: String,
              val url: String?,
              val started: Long?,
              val stopped: Long?,
              val total: Int?,
              val success: Int?,
              val fail: Int?) {
    class Builder(val testName: String) {
        private var url: String? = null
        private var started: Long? = null
        private var stopped: Long? = null
        private var total: Int? = null
        private var success: Int? = null
        private var fail: Int? = null

        fun url(url: String) = apply { this.url = url }
        fun started(started: Long?) = apply { this.started = started }
        fun stopped(stopped: Long?) = apply { this.stopped = stopped }
        fun fail(fail: Int?) = apply { this.fail = fail }
        fun total(total: Int?) = apply { this.total = total }
        fun success(success: Int?) = apply { this.success = success }

        fun build(): Result = Result(testName, url, started, stopped, total, success, fail)
    }
}
