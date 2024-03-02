package ru.practicum.android.diploma.filter.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.core.data.NetworkClient
import ru.practicum.android.diploma.core.data.mapper.VacancyMapper
import ru.practicum.android.diploma.core.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.core.data.network.dto.CountryResponse
import ru.practicum.android.diploma.core.domain.model.Country
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.util.Resource

class FilterRepositoryImpl(private val networkClient: NetworkClient) : FilterRepository {
    override fun getCountries(): Flow<Resource<List<Country>>> = flow {
        val response = networkClient.getCountries()
        when (response.resultCode) {
            RetrofitNetworkClient.SUCCESSFUL_CODE -> {
                val countryResponse = response as CountryResponse
                val domainModel = countryResponse.countries.map { countryDto -> VacancyMapper.mapToDomain(countryDto) }
                emit(Resource.Success(data = domainModel))
            }

            RetrofitNetworkClient.NETWORK_ERROR_CODE -> {
                emit(Resource.InternetError())
            }

            RetrofitNetworkClient.EXCEPTION_ERROR_CODE -> {
                emit(Resource.ServerError())
            }
        }
    }
}
