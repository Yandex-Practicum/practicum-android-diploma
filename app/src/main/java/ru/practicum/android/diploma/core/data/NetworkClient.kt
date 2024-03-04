package ru.practicum.android.diploma.core.data

import ru.practicum.android.diploma.core.data.network.dto.Response
import ru.practicum.android.diploma.core.domain.model.SearchFilterParameters

interface NetworkClient {
    suspend fun getVacanciesByPage(
        searchText: String,
        filterParameters: SearchFilterParameters,
        page: Int,
        perPage: Int = 20
    ): Response

    suspend fun getDetailVacancyById(id: Long): Response

    suspend fun getIndustries(): Response

    companion object {
        const val SUCCESSFUL_CODE = 200
        const val EXCEPTION_ERROR_CODE = -2
        const val NETWORK_ERROR_CODE = -1
    }
}
