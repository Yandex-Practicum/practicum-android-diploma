package ru.practicum.android.diploma.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.dto.VacanciesResponseDto

object RetrofitClient {

    private const val BASE_URL = "https://api.hh.ru/"

    private val client: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val api: HhApi by lazy {
        client.create(HhApi::class.java)
    }

    suspend fun doRequest(): VacanciesResponseDto? {
        return withContext(Dispatchers.IO) {
            try {
                api.searchVacancies()
            } catch (e: HttpException) {
                Log.w("HttpException", e)
                null
            }
        }
    }
}
