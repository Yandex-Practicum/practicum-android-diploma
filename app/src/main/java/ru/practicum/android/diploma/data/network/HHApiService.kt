package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.Response
import ru.practicum.android.diploma.data.VacanciesSearchResponse
import ru.practicum.android.diploma.data.models.VacancyDetailDto

interface HHApiService {
    @Headers(
        "Authorization: Bearer {token}",
        "HH-User-Agent: Application Name ({appName})"
    )
    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancy(
        @Path("vacancy_id") id: String,
        token: String,
        appName: String
    ): VacancyDetailDto

    @Headers(
        "Authorization: Bearer {token}",
        "HH-User-Agent: Application Name ({appName})"
    )
    @GET("/vacancies")
    suspend fun searchVacancies(
        @QueryMap options: Map<String, String>,
        token: String,
        appName: String,
    ): VacanciesSearchResponse
}
