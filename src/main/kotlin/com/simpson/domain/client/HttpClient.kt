package com.simpson.domain.client

import io.github.rybalkinsd.kohttp.dsl.httpPost
import io.github.rybalkinsd.kohttp.ext.url
import okhttp3.Response

class HttpClient constructor(val targetUrl: String) {
    private val jwtTag: String = "JWT_TAG"

    fun sendPostMessageApiChatMsg(message: String ): Boolean {
        val path = "/api/v1/relaymsg"
        val response: Response = httpPost {
            url(targetUrl + path)

            header {
                "Accept" to "*/*"
                "Connection" to "keep-alive"
                "Connect-Type" to "text/plain"
                "Accept-Encoding" to "gzip,deflate,br"
                "User-Agent" to "APIServer_Load_Generator"
                "Authorization" to jwtTag
            }

            body {
                json {    //{
                    "content" to message
                }         //}
            }
        }
        if (!response.isSuccessful) {
            println(response)
        }
        response.close()
        return if(response.isSuccessful()) true else false
    }

    fun sendPostRegistUserApi(num: Int,
                              umail: String,
                              deviceId: String,
                              phoneNo: String): Boolean {
        val path = "/api/v1/userregist"
        val response: Response = httpPost {
            url(targetUrl + path)

            header {
                "Accept" to "*/*"
                "Connection" to "keep-alive"
                "Connect-Type" to "application/json"
                "Content-Type" to "application/x-www-form-urlencoded"
                "Accept-Encoding" to "application/zip"
                "Content-Transfer-Encoding" to "application/zip"
                "User-Agent" to "APIServer_Load_Generator"
                "Authorization" to jwtTag
            }

            body {
                json {                              //{
                    "version" to "0.1"              //  "app_version":"0",
                    "umail" to umail                //  "umail":"[USER_PASSWORD]",
                    "password" to "[USER_PASSWORD]" //  "password":"[USER_PASSWORD]",
                    "profile" to  "[USER_PROFILE]"  //  "profile_message":"[USER_PROFILE]",
                    "name" to "[USER_NAME]"         //  "account_name":"USER_NAME]"
                }                                   //}
            }
        }
        if (!response.isSuccessful) {
            println(response)
        }
        response.close()
        return if(response.isSuccessful()) true else false
    }
}
