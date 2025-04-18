package ru.practicum.android.diploma.data.network

import retrofit2.Response
import ru.practicum.android.diploma.data.IRetrofitApiClient
import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.data.network.dto.GetAreasRequest
import ru.practicum.android.diploma.data.network.dto.GetIndustriesRequest
import ru.practicum.android.diploma.data.network.dto.GetVacancyDetailsRequest
import ru.practicum.android.diploma.data.network.dto.GetVacancyDetailsResponse
import ru.practicum.android.diploma.data.network.dto.SearchVacanciesRequest
import ru.practicum.android.diploma.data.network.dto.SearchVacanciesResponse
import ru.practicum.android.diploma.data.network.dto.toQueryParams
import ru.practicum.android.diploma.util.handleRequest

class RetrofitApiClient(
    private val api: IApiService
) : IRetrofitApiClient {

    override suspend fun searchVacancies(req: SearchVacanciesRequest): Response<SearchVacanciesResponse> {
        return handleRequest {
            api.searchVacancies(req.toQueryParams())
        }
    }

    override suspend fun getVacancyDetails(req: GetVacancyDetailsRequest): Response<GetVacancyDetailsResponse> {
        return handleRequest {
            api.getVacancyDetails(id = req.vacancyId)
        }
    }

    override suspend fun getAreas(req: GetAreasRequest): Response<ArrayList<AreaDto>> {
        return handleRequest {
            api.getAreas(locale = req.locale)
        }
    }

    override suspend fun getIndustries(req: GetIndustriesRequest): Response<ArrayList<IndustryDto>> {
        return handleRequest {
            api.getIndustries(locale = req.locale)
        }
    }
}
