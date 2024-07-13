package ru.practicum.android.diploma.search.data.network.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import java.nio.charset.Charset
import kotlin.math.min

object LoggingInterceptor : Interceptor {
    private val CHARSET: Charset = Charset.forName("UTF-8")
    private const val MAX_LOGCAT_SIZE = 4_000L
    private const val LOGCAT_TAG = "LoggingInterceptor"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        logRequest(request)

        val response = chain.proceed(request)
        logResponse(response)
        return response
    }

    private fun logRequest(request: Request) {
        logD("request =>\n${request.url()}")
        val buffer = Buffer()
        request.body()?.writeTo(buffer)
        while (!buffer.exhausted()) {
            logD(buffer.readString(min(MAX_LOGCAT_SIZE, buffer.size()), CHARSET))
        }
    }

    private fun logResponse(response: Response) {
        response.body()?.apply {
            source().request(Long.MAX_VALUE)
            val buffer = source().buffer.clone()
            logD("response =>\n")
            while (!buffer.exhausted()) {
                logD(buffer.readString(min(MAX_LOGCAT_SIZE, buffer.size()), CHARSET))
            }
        }
    }

    private fun logD(message: String) {
        Log.d(LOGCAT_TAG, message)
    }
}
