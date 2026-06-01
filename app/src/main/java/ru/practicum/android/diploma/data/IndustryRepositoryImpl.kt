package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.data.dto.IndustriesRequest
import ru.practicum.android.diploma.domain.api.IndustryRepository
import ru.practicum.android.diploma.domain.models.IndustryResult

class IndustryRepositoryImpl(
    private val networkClient: NetworkClient,
) : IndustryRepository {

    override suspend fun getIndustries(): IndustryResult {
        val response = networkClient.doRequest(IndustriesRequest)
        return when (response.resultCode) {
            NO_INTERNET -> IndustryResult.NoInternet
            HTTP_SERVER_ERROR -> IndustryResult.ServerError
            HTTP_OK -> {
                val data = response.data as? List<FilterIndustryDto>
                if (data == null) {
                    IndustryResult.Error
                } else {
                    IndustryResult.Success(data.toDomain())
                }
            }
            else -> IndustryResult.Error
        }
    }

    private companion object {
        const val HTTP_OK = 200
        const val NO_INTERNET = -1
        const val HTTP_SERVER_ERROR = 500
    }
}
