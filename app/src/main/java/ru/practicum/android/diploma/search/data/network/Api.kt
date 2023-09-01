package ru.practicum.android.diploma.search.data.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.search.data.dto.SearchResponse
import ru.practicum.android.diploma.util.app.App
import java.util.Properties

interface Api {

   /* @GET("/vacancies?text=text")
    suspend fun search(@Query("term") text: String): SearchResponse*/

    @Headers(
        "Authorization: Bearer YOUR_TOKEN",
        "HH-User-Agent: Application Name (name@example.com)"
    )
    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancyById(@Path("vacancy_id") id: String): SearchResponse


    /* @Headers(
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

    @Headers(
        "Authorization: Bearer APPLN5BAACU8JSNDLOLGES6S06DHA1OHG61A0PHCQHR01P8J9G6I4076P2URU0SA",
        "HH-User-Agent: EmployMe (gerzag96@gmail.com)"
    )
    @GET("/vacancies")
    suspend fun search(@Query("searchRequest") text: String,
                       @Query("page") page: Int,
                       @Query("per_page") perPage: Int): SearchResponse

    @GET("/vacancies")
    suspend fun getVacancies(@QueryMap options: Map<String, String>): SearchResponse


  /*  @POST("https://hh.ru/oauth/token")
    fun authenticate(
        @Query ("grant_type = client_credentials&client_id=OCAQO5VQLHQEPBBNBNVAON39KO454GJK9TIHLF7HLNREHNQ1JLVSNL48RB6C1G2P&client_secret=GGUL1U22NR22605R8V3QI95TLNT1PH926SRRA7COD1Q0ALTRHFFV9029G5S2QVVI"
    ): SearchResponse*/


    companion object {
        val token = "APPLN5BAACU8JSNDLOLGES6S06DHA1OHG61A0PHCQHR01P8J9G6I4076P2URU0SA"
            //Properties().getProperty("hhAccessToken")
    }
}