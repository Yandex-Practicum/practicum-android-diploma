package ru.practicum.android.diploma.core.data.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ru.practicum.android.diploma.BuildConfig

object HhApiRetrofitBuilder {
    private const val BASE_URL = "https://api.hh.ru/"
    private val headerInterceptor = Interceptor { chain ->
        chain.run {
            proceed(
                request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer ${BuildConfig.HH_ACCESS_TOKEN}")
                    .addHeader(
                        "HH-User-Agent",
                        "JobSeeker/${BuildConfig.VERSION_NAME} (${BuildConfig.DEVELOPER_EMAIL})"
                    )
                    .build()
            )
        }
    }

    fun buildRetrofit(): HhApi {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(headerInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create()
    }
}
