package ru.practicum.android.diploma.data

import retrofit2.Response
import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.data.network.dto.GetAreasRequest
import ru.practicum.android.diploma.data.network.dto.GetIndustriesRequest
import ru.practicum.android.diploma.data.network.dto.GetVacancyDetailsRequest
import ru.practicum.android.diploma.data.network.dto.GetVacancyDetailsResponse
import ru.practicum.android.diploma.data.network.dto.SearchVacanciesRequest
import ru.practicum.android.diploma.data.network.dto.SearchVacanciesResponse

interface IRetrofitApiClient {

    suspend fun searchVacancies(req: SearchVacanciesRequest): Response<SearchVacanciesResponse>
    suspend fun getVacancyDetails(req: GetVacancyDetailsRequest): Response<GetVacancyDetailsResponse>
    suspend fun getAreas(req: GetAreasRequest): Response<ArrayList<AreaDto>>
    suspend fun getIndustries(req: GetIndustriesRequest): Response<ArrayList<IndustryDto>>
}
