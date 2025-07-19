package ru.practicum.android.diploma.data.models.areas

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface AreasApi {
    @Headers("HH-User-Agent: WorkNest/1.0 (danilov-av2004@mail.ru)")
    @GET("areas/countries")
    suspend fun getCountries(): List<AreasDto>

    @Headers("HH-User-Agent: WorkNest/1.0 (danilov-av2004@mail.ru)")
    @GET("areas/{country_id}")
    suspend fun getRegions(@Path("country_id") countryId: String): AreaWithSubareasDto
}
