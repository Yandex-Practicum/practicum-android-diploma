package ru.practicum.android.diploma.search.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://api.hh.ru/"
    private const val TOKEN = "APPLOB7KQGVDGB6HB4KF7CDUB462UC7PEJ092HD2NL80LL4URBFIKQ68D20DFORU"

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

