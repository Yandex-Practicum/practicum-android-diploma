package ru.practicum.android.diploma.data.models.areas.countryscreen.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.mappers.toDomain
import ru.practicum.android.diploma.data.models.areas.countryscreen.CountriesRequest
import ru.practicum.android.diploma.data.models.areas.countryscreen.CountriesResponseDto
import ru.practicum.android.diploma.data.vacancysearchscreen.impl.ErrorType
import ru.practicum.android.diploma.data.vacancysearchscreen.network.NetworkClient
import ru.practicum.android.diploma.domain.filters.model.Country
import ru.practicum.android.diploma.domain.filters.repository.FiltersRepository
import ru.practicum.android.diploma.util.Resource

class FiltersRepositoryImpl(private val networkClient: NetworkClient) : FiltersRepository {
    override fun getCountries(): Flow<Resource<List<Country>>> = flow {
        val response = networkClient.doRequest(CountriesRequest)
        when (response.resultCode) {
            SEARCH_SUCCESS -> {
                val data = (response as CountriesResponseDto).countries.map { it.toDomain() }
                emit(Resource.Success(data))
            }

            NO_CONNECTION -> emit(Resource.Error(ErrorType.NO_INTERNET))
            SERVER_ERROR -> emit(Resource.Error(ErrorType.SERVER_ERROR))
            else -> emit(Resource.Error(ErrorType.UNKNOWN))
        }
    }

    companion object {
        private const val NO_CONNECTION = -1
        private const val SEARCH_SUCCESS = 2
        private const val SERVER_ERROR = 5
    }
}
