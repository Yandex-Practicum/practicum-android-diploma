package ru.practicum.android.diploma.data.network

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import ru.practicum.android.diploma.BuildConfig
import java.util.concurrent.TimeUnit

class NetworkClient(
    private val retrofit: Retrofit
) {
    val apiService: ApiService = retrofit.create(ApiService::class.java)

    private fun createOkHttpClient(): OkHttpClient {
        val token = BuildConfig.API_ACCESS_TOKEN
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request: Request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(request)
            }
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    private companion object {
        private const val TIMEOUT_SECONDS = 30L
    }
}
