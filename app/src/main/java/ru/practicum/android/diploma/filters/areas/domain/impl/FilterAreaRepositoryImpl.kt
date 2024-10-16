package ru.practicum.android.diploma.filters.areas.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.filters.areas.data.FilterAreasRequest
import ru.practicum.android.diploma.filters.areas.data.FilterAreasResponse
import ru.practicum.android.diploma.filters.areas.data.dto.converter.ConverterForAreas
import ru.practicum.android.diploma.filters.areas.domain.api.FilterAreaRepository
import ru.practicum.android.diploma.filters.areas.domain.models.Area
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.network.HttpStatusCode
import ru.practicum.android.diploma.util.network.NetworkClient

class FilterAreaRepositoryImpl(
    private val networkClient: NetworkClient,
    private val converter: ConverterForAreas
) : FilterAreaRepository {

    override fun getAreas(): Flow<Resource<List<Area>>> = flow {
        val response = networkClient.doRequest(FilterAreasRequest())
        emit(
            when (response.resultCode) {
                HttpStatusCode.OK -> {
                    val countries = (response as FilterAreasResponse).areas.map {
                        converter.map(it)
                    }

                    if (countries.isEmpty()) {
                        Resource.Error(HttpStatusCode.NOT_FOUND, null)
                    } else {
                        Resource.Success(countries)
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
