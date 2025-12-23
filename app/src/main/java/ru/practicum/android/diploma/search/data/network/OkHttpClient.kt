package ru.practicum.android.diploma.search.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://your-api-url.com")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
