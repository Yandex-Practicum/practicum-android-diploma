package ru.practicum.android.diploma.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacanciesRequest

class RetrofitNetworkClient() : NetworkClient {

    private val hhBaseUrl = "https://api.hh.ru"

    private val retrofit = Retrofit.Builder()
        .baseUrl(hhBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(HHApi::class.java)

    override suspend fun doRequest(dto: Any): Response {

        if (dto !is VacanciesRequest){
            return Response().apply { resultCode = 400 }
        }

        val response = retrofit.getVacancies(dto.page)
        response.apply { resultCode = 200 }

        return response

    }
}
