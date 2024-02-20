package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.vacancyDetail.dto.DetailResponse
import ru.practicum.android.diploma.data.vacancyList.dto.VacanciesSearchResponse

interface JobVacancySearchApi {

    companion object {
        const val HEADER_AUTH = "Authorization: Bearer YOUR_TOKEN"
        const val HEADER_USER = "HH-User-Agent: Application Name (name@example.com)"
    }

    // Запрос списка вакансий
    @Headers(HEADER_AUTH, HEADER_USER)
    @GET("/vacancies")
    suspend fun getVacancyList(@QueryMap options: HashMap<String, String>): VacanciesSearchResponse

    // Запрос детальной информации о вакансии
    @Headers(HEADER_AUTH, HEADER_USER)
    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancyDetail(@Path("vacancy_id") id: String): DetailResponse
}
