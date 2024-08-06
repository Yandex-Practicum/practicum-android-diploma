package ru.practicum.android.diploma.data.interceptors

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import ru.practicum.android.diploma.BuildConfig

object HeaderInterceptor : Interceptor {
    private const val USER_AGENT_AUTHORIZATION = "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}"
    private const val USER_AGENT_APP_NAME = "HH-User-Agent: Find Your Job (snt_mail@bk.ru)"

    private val headers = Headers.Builder()
        .add(USER_AGENT_AUTHORIZATION)
        .add(USER_AGENT_APP_NAME)
        .build()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .headers(headers)
            .build()

        return chain.proceed(request)
    }
}
