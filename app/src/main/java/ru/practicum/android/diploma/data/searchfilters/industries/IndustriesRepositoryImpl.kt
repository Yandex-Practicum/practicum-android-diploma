package ru.practicum.android.diploma.data.searchfilters.industries

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.mappers.toDomain
import ru.practicum.android.diploma.data.models.industries.remote.IndustryRequest
import ru.practicum.android.diploma.data.models.industries.remote.IndustryResponseDto
import ru.practicum.android.diploma.data.vacancysearchscreen.impl.ErrorType
import ru.practicum.android.diploma.data.vacancysearchscreen.network.NetworkClient
import ru.practicum.android.diploma.domain.models.industries.Industry
import ru.practicum.android.diploma.domain.searchfilters.industries.IndustriesRepository
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.DebounceConstants.NO_CONNECTION
import ru.practicum.android.diploma.util.DebounceConstants.SEARCH_SUCCESS
import ru.practicum.android.diploma.util.DebounceConstants.SERVER_ERROR

class IndustriesRepositoryImpl(private val networkClient: NetworkClient) : IndustriesRepository {
    override fun getIndustries(): Flow<Resource<List<Industry>>> = flow {
        val response = networkClient.doRequest(IndustryRequest)
        when (response.resultCode) {
            SEARCH_SUCCESS -> {
                val data = (response as IndustryResponseDto).industries
                    .flatMap { it.industries }
                    .map { it.toDomain() }
                emit(Resource.Success(data))
            }

            NO_CONNECTION -> emit(Resource.Error(ErrorType.NO_INTERNET))
            SERVER_ERROR -> emit(Resource.Error(ErrorType.SERVER_ERROR))
            else -> emit(Resource.Error(ErrorType.UNKNOWN))
        }
    }
}
