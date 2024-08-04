package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.dto.Responce

const val USER_AGENT_AUTHORIZATION = "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}"
const val USER_AGENT_APP_NAME = "HH-User-Agent: CareerHub (e.gasymov@hh.ru)"

interface HHApiService {
    @Headers(USER_AGENT_AUTHORIZATION, USER_AGENT_APP_NAME)
    @GET("vacancies/{vacancy_id}")
    suspend fun getVacancy(@Path("vacancy_id") id: Int): Responce

    @Headers(USER_AGENT_AUTHORIZATION, USER_AGENT_APP_NAME)
    @GET("vacancies")
    suspend fun searchVacancies(@QueryMap options: Map<String, String>): Responce
}
