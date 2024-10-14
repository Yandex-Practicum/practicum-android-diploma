package ru.practicum.android.diploma.filters.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import ru.practicum.android.diploma.filters.data.dto.FilterAreasRequest
import ru.practicum.android.diploma.filters.data.dto.FilterAreasResponse
import ru.practicum.android.diploma.filters.data.dto.converter.ConverterForAreas
import ru.practicum.android.diploma.filters.domain.api.FilterAreaRepository
import ru.practicum.android.diploma.filters.domain.models.Area
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.network.HttpStatusCode
import ru.practicum.android.diploma.util.network.NetworkClient

class FilterAreaRepositoryImpl(
    private val networkClient: NetworkClient,
    private val converter: ConverterForAreas
) : FilterAreaRepository {

    private var cachedRegions: Resource<List<Area>>? = null

    override fun getAreas(): Flow<Resource<List<Area>>> = flow {
        val response = networkClient.doRequest(FilterAreasRequest())

        emit(
            when (response.resultCode) {
                HttpStatusCode.OK -> {
                    val areasResponse = response as FilterAreasResponse

                    val countries = areasResponse.areas.map {
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

    override fun loadAllRegions(): Flow<Resource<List<Area>>> = flow {
        getAreas().collect { resource ->
            cachedRegions = resource
            emit(resource)
        }
    }

    override fun getAllRegions(): Flow<Resource<List<Area>>> {
        cachedRegions?.let { resource ->
            return flowOf(resource)
        }
        return loadAllRegions()
    }
}
