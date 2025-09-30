package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import ru.practicum.android.diploma.data.dto.VacancyDto

interface ApiService {
    @GET("vacancies")
    suspend fun getVacancies(): List<VacancyDto>
}
