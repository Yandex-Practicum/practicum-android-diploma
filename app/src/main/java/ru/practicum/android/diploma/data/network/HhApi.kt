package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.data.dto.VacanciesResponseDto
import ru.practicum.android.diploma.data.dto.VacancyDto

interface HhApi {
    @Headers(
        "Authorization: Bearer " + BuildConfig.HH_ACCESS_TOKEN,
        "HH-User-Agent: heheru (heheru2025@gmail.com)"
    )
    @GET("vacancies")
    suspend fun searchVacancies(
        @Query("text") text: String?,
        @QueryMap optionsForString: Map<String, String>,
        @QueryMap optionsForInt: Map<String, Int>,
//        @Query("text") text: String,
//        @Query("area") area: String? = null,
//        @Query("industry") industry: String? = null,
//        @Query("salary") salary: Int? = null,
        @Query("only_with_salary") onlyWithSalary: Boolean = false,
        @Query("per_page") perPage: Int = 20
//        @Query("page") page: Int = 0
    ): VacanciesResponseDto

    @Headers(
        "Authorization: Bearer " + BuildConfig.HH_ACCESS_TOKEN,
        "HH-User-Agent: heheru (heheru2025@gmail.com)"
    )
    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancyDetails(@Path("vacancy_id") id: String): VacancyDto

    @Headers(
        "Authorization: Bearer " + BuildConfig.HH_ACCESS_TOKEN,
        "HH-User-Agent: heheru (heheru2025@gmail.com)"
    )
    @GET("/areas")
    suspend fun getAreas(): List<AreaDto>

    @Headers(
        "Authorization: Bearer " + BuildConfig.HH_ACCESS_TOKEN,
        "HH-User-Agent: heheru (heheru2025@gmail.com)"
    )
    @GET("/areas/{area_id}")
    suspend fun getAreaInfo(@Path("area_id") id: String): AreaDto

    @Headers(
        "Authorization: Bearer " + BuildConfig.HH_ACCESS_TOKEN,
        "HH-User-Agent: heheru (heheru2025@gmail.com)"
    )
    @GET("/industries")
    suspend fun getIndustries(): List<IndustryDto>
}
