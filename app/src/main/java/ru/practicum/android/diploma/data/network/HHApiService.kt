package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.VacanciesSearchResponse
import ru.practicum.android.diploma.data.dto.VacancyDetailDto

interface HHApiService {
    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancy(@Path("vacancy_id") id: String,): VacancyDetailDto

    @GET("/vacancies")
    suspend fun searchVacancies(@QueryMap options: Map<String, String>): VacanciesSearchResponse
}
