package ru.practicum.android.diploma.root.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import ru.practicum.android.diploma.features.vacancydetails.data.models.VacancyDetailsResponse

interface HeadHunterApi {

// waiting for token
    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancyById(
        @Path("vacancy_id") vacancyId: String
    ): VacancyDetailsResponse

}