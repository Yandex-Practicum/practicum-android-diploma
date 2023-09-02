package ru.practicum.android.diploma.root.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import ru.practicum.android.diploma.features.vacancydetails.data.models.VacancyDetailsDto

interface HeadHunterApi {

    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancyById(@Path("vacancy_id") vacancyId: String): VacancyDetailsDto

}