package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.practicum.android.diploma.data.dto.NetworkResponse

interface HhApi {
    @GET("vacancies")
    suspend fun searchVacancies(
        @Query("text") text: String,
        @Query("area") area: String? = null,
        @Query("industry") industry: String? = null,
        @Query("salary") salary: Int? = null,
        @Query("only_with_salary") onlyWithSalary: Boolean = false,
        @Query("per_page") perPage: Int = 20,
        @Query("page") page: Int = 0
    ): NetworkResponse
}
