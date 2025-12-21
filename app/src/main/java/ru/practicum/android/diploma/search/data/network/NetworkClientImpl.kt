package ru.practicum.android.diploma.search.data.network

import ru.practicum.android.diploma.search.data.dto.FilterAreaDto
import ru.practicum.android.diploma.search.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.search.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.search.data.dto.VacancyResponseDto
import ru.practicum.android.diploma.search.utils.NetworkConnectionChecker

class NetworkClientImpl(
    private val vacancyApi: VacancyApi,
    private val networkConnectionChecker: NetworkConnectionChecker
) : NetworkClient {

    override suspend fun getAreas(): Resource<List<FilterAreaDto>> {
        return safeApiCall(networkConnectionChecker) { vacancyApi.getAreas() }
    }

    override suspend fun getIndustry(): Resource<List<FilterIndustryDto>> {
        return safeApiCall(networkConnectionChecker) { vacancyApi.getIndustry() }
    }

    override suspend fun getVacancies(): Resource<VacancyResponseDto> {
        return safeApiCall(networkConnectionChecker) { vacancyApi.getVacancies() }
    }

    override suspend fun getVacancyById(id: String): Resource<VacancyDetailDto> {
        return safeApiCall(networkConnectionChecker) { vacancyApi.getVacancyById(id) }
    }

}
