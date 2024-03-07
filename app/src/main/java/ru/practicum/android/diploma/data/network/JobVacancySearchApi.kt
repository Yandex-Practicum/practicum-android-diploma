package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.response.AreaDto
import ru.practicum.android.diploma.data.response.ParentIndustries
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.VacancyDetailDtoResponse
import ru.practicum.android.diploma.data.vacancylist.dto.VacanciesSearchResponse

interface JobVacancySearchApi {

    // Запрос списка вакансий
    @GET("/vacancies")
    suspend fun getFullListVacancy(@QueryMap response: Map<String, String>): VacanciesSearchResponse

    // Запрос детальной информации о вакансии
    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancyDetail(@Path("vacancy_id") id: String): VacancyDetailDtoResponse

    // Запрос списка областей
    @GET("/areas")
    suspend fun getAllAreas(): List<AreaDto>

    // Запрос списка регионов
    @GET("/areas/{areas_id}")
    suspend fun getAreaId(@Path("areas_id") areasId: String): AreaDto

    // Запрос списка вакансий
    @GET("/industries")
    suspend fun getAllIndustries(): List<ParentIndustries>
}
