package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.practicum.android.diploma.data.dto.VacanciesResponse

interface HHApi {

    @GET("vacancies")
    suspend fun getVacancies(
        //@Header("Authorization") token: String
        @Query("page") page: Int //Номер страницы списка вакансий
    ): VacanciesResponse


}
