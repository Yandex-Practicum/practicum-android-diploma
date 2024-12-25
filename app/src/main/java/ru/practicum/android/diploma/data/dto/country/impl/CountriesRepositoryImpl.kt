package ru.practicum.android.diploma.data.dto.country.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converters.CountriesConverter
import ru.practicum.android.diploma.data.dto.network.RetrofitNetworkClient
import ru.practicum.android.diploma.data.dto.request.CountriesRequest
import ru.practicum.android.diploma.data.dto.response.CountriesResponse
import ru.practicum.android.diploma.domain.NetworkClient
import ru.practicum.android.diploma.domain.api.country.CountriesRepository
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.ui.search.state.VacancyError
import ru.practicum.android.diploma.util.Resource

class CountriesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val countriesConverter: CountriesConverter
) : CountriesRepository {

    override fun getCountries(): Flow<Resource<List<Country>>> = flow {
        val response = networkClient.doRequest(CountriesRequest())
        when (response.code) {
            RetrofitNetworkClient.INTERNET_NOT_CONNECT -> {
                emit(Resource.Error(message = VacancyError.NETWORK_ERROR))
            }

            RetrofitNetworkClient.HTTP_OK_CODE -> {
                with(response as CountriesResponse) {
                    val data = countriesConverter.map(response)
                    emit(Resource.Success(data))
                }
            }

            else -> emit(Resource.Error(message = VacancyError.SERVER_ERROR))
        }
    }
}
