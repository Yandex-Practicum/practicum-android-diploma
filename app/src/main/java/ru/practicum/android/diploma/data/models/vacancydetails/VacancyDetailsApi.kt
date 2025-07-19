package ru.practicum.android.diploma.data.models.vacancydetails

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface VacancyDetailsApi {
    @Headers("HH-User-Agent: WorkNest/1.0 (danilov-av2004@mail.ru)")
    @GET("vacancies/{vacancy_id}")
    suspend fun getVacancyDetails(
        @Path("vacancy_id") id: String
    ): VacancyDetailsResponseDto
}
