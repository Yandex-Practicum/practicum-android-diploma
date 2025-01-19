package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacancyResponse

interface VacancyApi {
    @Headers("Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: Application 17_android_team_4 (name@example.com)")
    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancy(@Path("vacancy_id") id: String): Response
    @Headers("HH-User-Agent: Application 17_android_team_4")
    @GET("/vacancies")
    suspend fun searchVacancy(@QueryMap options: Map<String, String>): VacancyResponse
}
