package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.ApiResponse
import ru.practicum.android.diploma.data.network.dto.GetAreasRequest
import ru.practicum.android.diploma.data.network.dto.GetIndustriesRequest
import ru.practicum.android.diploma.data.network.dto.GetVacancyDetailsRequest
import ru.practicum.android.diploma.data.network.dto.SearchVacanciesRequest

interface IRetrofitApiClient {

    suspend fun searchVacancies(req: SearchVacanciesRequest): ApiResponse
    suspend fun getVacancyDetails(req: GetVacancyDetailsRequest): ApiResponse
    suspend fun getAreas(req: GetAreasRequest): ApiResponse
    suspend fun getIndustries(req: GetIndustriesRequest): ApiResponse
}
