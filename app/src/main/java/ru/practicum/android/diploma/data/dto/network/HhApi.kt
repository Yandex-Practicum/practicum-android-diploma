package ru.practicum.android.diploma.data.dto.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.dto.HhResponse
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.model.VacancyDto

const val token = BuildConfig.HH_ACCESS_TOKEN
const val email = BuildConfig.EMAIL
const val applicationName = BuildConfig.APPLICATION_NAME

interface HhApi {
        @Headers(
            "Authorization: Bearer $token",
            "HH-User-Agent: ${applicationName}(${email})"
        )
        @GET("/vacancies/{vacancy_id}")
        suspend fun getVacancyById(@Path("vacancy_id") vacancyId: String): VacancyDto


    @Headers(
        "Authorization: Bearer $token",
        "HH-User-Agent: ${applicationName}(${email})"
    )
    @GET("/vacancies")
    suspend fun getVacancies(@Query("text") vacancyName:String): Response
}
