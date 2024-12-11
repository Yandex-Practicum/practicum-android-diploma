package ru.practicum.android.diploma.data.dto.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.domain.NetworkClient

class RetrofitNetworkClient: NetworkClient {

    private val hhBaseURL = "https://api.hh.ru/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(hhBaseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val itunesService = retrofit.create(HhApi::class.java)

    override suspend fun doRequest(dto: Any): Response {
        TODO("Not yet implemented")
    }

}
