package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.converters.toModel
import ru.practicum.android.diploma.data.dto.FilterIndustriesRequest
import ru.practicum.android.diploma.data.dto.FilterIndustriesResponse
import ru.practicum.android.diploma.domain.api.FilterIndustriesRepository
import ru.practicum.android.diploma.domain.models.Industry

class FilterIndustriesRepositoryImpl(private var networkClient: NetworkClient) : FilterIndustriesRepository {
    override fun getIndustries(query: String): Flow<List<Industry>> = flow {
        val request = FilterIndustriesRequest(query)
        val data = networkClient.filterIndustryRequest(request)
        if (data.resultCode == SUCCESS_CODE && data is FilterIndustriesResponse) {
            val filteredIndustries = if (query.isNotEmpty()) {
                data.toModel().filter { industry ->
                    industry.industryName.contains(query, ignoreCase = true)
                }
            } else {
                data.toModel()
            }
            emit(filteredIndustries)
        } else {
            emit(emptyList())
        }
    }

    companion object {
        private const val SUCCESS_CODE = 200
    }
}
