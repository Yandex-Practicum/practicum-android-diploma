package ru.practicum.android.diploma.data.dto.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.practicum.android.diploma.data.dto.HhResponse


const val termForGetVacancies = "vacancies"
const val termForGetIndistries = "industries"

interface HhApi {
    @GET("/search?entity=song ")
    suspend fun search(@Query(termForGetVacancies) text: String) : HhResponse
}
