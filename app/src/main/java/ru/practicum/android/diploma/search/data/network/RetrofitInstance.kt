package ru.practicum.android.diploma.search.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val HH_URL = "https://api.hh.ru/"

object RetrofitInstance {

    val headHunterAPI: HeadHunterAPI by lazy {
        Retrofit.Builder()
            .baseUrl(HH_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HeadHunterAPI::class.java)
    }

}
