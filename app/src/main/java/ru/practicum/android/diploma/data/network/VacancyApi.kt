package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.VacanciesResponse
import ru.practicum.android.diploma.data.dto.VacancyDescriptionResponse

interface VacancyApi {
    @Headers("HH-User-Agent: Application Practicum_17_android_team")
    @GET("/vacancies/{vacancy_id}?locale=RU")
    suspend fun getVacancy(@Path("vacancy_id") id: String): VacancyDescriptionResponse

    @Headers("HH-User-Agent: Application Practicum_17_android_team")
    @GET("/vacancies")
    suspend fun searchVacancy(@QueryMap options: Map<String, String>): VacanciesResponse
}
