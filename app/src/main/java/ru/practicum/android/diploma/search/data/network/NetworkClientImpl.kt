package ru.practicum.android.diploma.search.data.network

import ru.practicum.android.diploma.search.data.dto.FilterAreaDto
import ru.practicum.android.diploma.search.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.search.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.search.data.dto.VacancyResponseDto
import ru.practicum.android.diploma.search.data.mapper.FilterMapper
import ru.practicum.android.diploma.search.utils.NetworkConnectionChecker

class NetworkClientImpl(
    private val vacancyApi: VacancyApi,
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val filterMapper: FilterMapper
) : NetworkClient {

    override suspend fun getAreas(): Resource<List<FilterAreaDto>> {
        return safeApiCall(networkConnectionChecker) { vacancyApi.getAreas() }
    }

    override suspend fun getIndustry(): Resource<List<FilterIndustryDto>> {
        return safeApiCall(networkConnectionChecker) { vacancyApi.getIndustry() }
    }

    override suspend fun getVacancies(
        area: Int?,
        industry: Int?,
        text: String?,
        salary: Int?,
        page: Int?,
        onlyWithSalary: Boolean?
    )
        : Resource<VacancyResponseDto> {
        return safeApiCall(networkConnectionChecker) {
            val queryMap = filterMapper.buildVacancyQueryMap(
                area = area,
                industry = industry,
                text = text,
                salary = salary,
                page = page,
                onlyWithSalary = onlyWithSalary
            )
            vacancyApi.getVacancies(queryMap)
        }
    }

    override suspend fun getVacancyById(id: String): Resource<VacancyDetailDto> {
        return safeApiCall(networkConnectionChecker) { vacancyApi.getVacancyById(id) }
    }

    object HttpStatusCodes {
        const val OK = 200
        const val BAD_REQUEST = 400
        const val UNAUTHORIZED = 401
        const val FORBIDDEN = 403
        const val NOT_FOUND = 404
        const val INTERNAL_SERVER_ERROR = 500
        const val SERVICE_UNAVAILABLE = 503
    }
}
