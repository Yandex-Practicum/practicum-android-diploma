package ru.practicum.android.diploma.filters.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.filters.data.dto.FilterCountryRequest
import ru.practicum.android.diploma.filters.data.dto.FilterCountryResponse
import ru.practicum.android.diploma.filters.domain.api.FilterCountryRepository
import ru.practicum.android.diploma.filters.domain.models.Country
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.network.HttpStatusCode
import ru.practicum.android.diploma.util.network.NetworkClient

class FilterCountryRepositoryImpl(
    private val networkClient: NetworkClient
) : FilterCountryRepository {

    override fun getAreas(): Flow<Resource<List<Country>>> = flow {
        val response = networkClient.doRequest(FilterCountryRequest())

        emit(
            when (response.resultCode) {
                HttpStatusCode.OK -> {
                    val areasResponse = response as FilterCountryResponse

                    val countries = areasResponse.areas
                        .filter { it.parentId == null }
                        .map { areaDto -> Country(areaDto.id, areaDto.name) }

                    if (countries.isEmpty()) {
                        Resource.Error(HttpStatusCode.NOT_FOUND, null)
                    } else {
                        Resource.Success(countries) // Заебись
                    }
                }

                HttpStatusCode.NOT_CONNECTED -> {
                    Resource.Error(HttpStatusCode.NOT_CONNECTED, null)
                }

                else -> {
                    Resource.Error(response.resultCode)
                }
            }
        )
    }
}
