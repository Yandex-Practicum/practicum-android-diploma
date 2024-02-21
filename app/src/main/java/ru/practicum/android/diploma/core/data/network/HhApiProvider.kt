package ru.practicum.android.diploma.core.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object HhApiProvider {
    private const val BASE_URL = "https://api.hh.ru/"
    val hhService: HhApi by lazy { buildRetrofit() }

    private fun buildRetrofit(): HhApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}
