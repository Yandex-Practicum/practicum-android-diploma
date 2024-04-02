package ru.practicum.android.diploma.data.filter.country.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import ru.practicum.android.diploma.data.converter.AreaConverter.mapToCountryList
import ru.practicum.android.diploma.data.filter.country.CountryRequest
import ru.practicum.android.diploma.data.filter.country.response.AreasResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.vacancies.response.ResponseCodes
import ru.practicum.android.diploma.domain.country.Country
import ru.practicum.android.diploma.domain.country.CountryRepository
import ru.practicum.android.diploma.util.Resource

class CountryRepositoryImpl(
    val networkClient: NetworkClient
) : CountryRepository {

    override fun searchRegion(): Flow<Resource<List<Country>>> = flow {
        val response = networkClient.doRequestFilter(CountryRequest)

        when (response.resultCode) {
            ResponseCodes.DEFAULT -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.SUCCESS -> {
                try {
                    emit(
                        Resource.Success(
                            (response as AreasResponse).area.mapToCountryList().sortedBy { it.id }.reversed()
                        )
                    )
                } catch (e: IOException) {
                    emit(Resource.Error(response.resultCode.code))
                    throw e
                }
            }

            ResponseCodes.ERROR -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.NO_CONNECTION -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.SERVER_ERROR -> emit(Resource.Error(response.resultCode.code))
        }
    }
}
