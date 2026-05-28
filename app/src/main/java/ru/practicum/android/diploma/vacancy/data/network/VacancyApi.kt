package ru.practicum.android.diploma.vacancy.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import ru.practicum.android.diploma.vacancy.data.dto.VacancyDetailsDto

interface VacancyApi {
    @GET("vacancies/{id}")
    suspend fun getVacancyById(@Path("id") id: String): VacancyDetailsDto
}
