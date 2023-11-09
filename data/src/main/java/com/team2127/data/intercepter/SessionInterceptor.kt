package com.team2127.data.intercepter

import android.util.Log
import com.team2127.data.source.CookieDataSource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

fun String?.isJsonObject():Boolean{
    return this?.startsWith("{") ==true && this.endsWith("}")
}
fun String?.isJsonArray():Boolean{
    return this?.startsWith("[") ==true && this.endsWith("]")
}

class MemoryInterceptor @Inject constructor(
    private val dataStore: CookieDataSource
)
: Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        runBlocking {
            val cookie = response.headers("Set-Cookie")
            if(cookie.isNotEmpty()){
                dataStore.setCookie(response.headers("Set-Cookie").toString().split(";")[4].split(" ")[2])
            }
        }
        return response
    }

}

class AddInterceptor @Inject constructor(
    private val datastore: CookieDataSource
) : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        runBlocking {
            val savedCookie = datastore.getCookie() ?: ""
            builder.addHeader("Cookie", savedCookie)
        }
        return chain.proceed(builder.build())
    }

}
