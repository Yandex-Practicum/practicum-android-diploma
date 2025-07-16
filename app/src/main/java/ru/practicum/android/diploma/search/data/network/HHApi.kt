package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import ru.practicum.android.diploma.vacancy.data.dto.VacancyDetailsDto

interface HHApi {
    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancyDetails(@Path("vacancy_id") id: String): VacancyDetailsDto
}
