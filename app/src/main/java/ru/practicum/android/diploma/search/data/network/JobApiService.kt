package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.search.data.dto.Resp
import ru.practicum.android.diploma.search.data.dto.SearchResponse

const val USER_AGENT_AUTHORIZATION = "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}"
const val USER_AGENT_APP_NAME = "HH-User-Agent: Find Your Job (snt_mail@bk.ru)"

interface JobApiService {

    @Headers(USER_AGENT_AUTHORIZATION, USER_AGENT_APP_NAME)
    @GET("vacancies/{vacancy_id}")
    suspend fun getVacancy(@Path("vacancy_id") id: Int): Resp

    @Headers(USER_AGENT_AUTHORIZATION, USER_AGENT_APP_NAME)
    @GET("vacancies")
    suspend fun searchVacancies(@QueryMap options: Map<String, String>): SearchResponse
}
