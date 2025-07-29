package ru.practicum.android.diploma.search.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.BuildConfig

object RetrofitClient {

    private const val BASE_URL = "https://api.hh.ru/"
    private const val TOKEN = BuildConfig.HH_ACCESS_TOKEN

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okhttpClient = OkHttpClient.Builder()
        .addInterceptor(TokenInterceptor(TOKEN))
        .addInterceptor(logging)
        .build()

    private val client: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttpClient)
            .build()
    }

    val hhApi: HHApi by lazy {
        client.create(HHApi::class.java)
    }
}
