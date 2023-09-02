package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.search.data.dto.SearchResponse

interface Api {

    /* Заготовка для поиска вакансий по id

    @Headers(
         "Authorization: Bearer YOUR_TOKEN",
         "HH-User-Agent: Application Name (name@example.com)"
     )
     @GET("/vacancies/{vacancy_id}")
     suspend fun getVacancyById(@Path("vacancy_id") id: String): SearchResponse*/

    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: EmployMe (gerzag96@gmail.com)"
    )
    @GET("/vacancies")
    suspend fun search(
        @Query("text") text: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): SearchResponse

    /*  Форма запроса для фильтров

    @Headers(
          "Authorization: Bearer YOUR_TOKEN",
          "HH-User-Agent: Application Name (name@example.com)"

          @GET("/vacancies")
     suspend fun getVacancies(@QueryMap options: Map<String, String>): SearchResponse*/

    /* Форма запроса для фильтров для понимания

    @Headers(
         "Authorization: Bearer YOUR_TOKEN",
         "HH-User-Agent: Application Name (name@example.com)"
     )
     @GET("/vacancies")
     suspend fun getVacancies(
         @Query("searchRequest") searchRequest: String,
         @Query("page") page: Int,
         @Query("per_page") perPage: Int,//20
         @Query("area") area: String,
         @Query("industry") industry: String,
         @Query("salary") salary: Int,
         @Query("only_with_salary") onlyWithSalary: Boolean,
     ): SearchResponse*/

}