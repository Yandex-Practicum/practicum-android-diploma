package ru.practicum.android.diploma.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.core.data.dto.AreasDto
import ru.practicum.android.diploma.core.data.network.NetworkClient
import ru.practicum.android.diploma.core.data.network.Request
import ru.practicum.android.diploma.core.data.network.ResultCode
import ru.practicum.android.diploma.core.domain.Resource
import ru.practicum.android.diploma.core.domain.models.Area
import ru.practicum.android.diploma.core.domain.repository.AreaRepository

class AreaRepositoryImpl(private val networkClient: NetworkClient) : AreaRepository {
    private var areasDto: AreasDto? = null

    override fun getCountries(): Flow<Resource<List<Area>>> = flow {
        if (!areasDto.isNullOrEmpty()) {
            emit(Resource.Success(mapContries(areasDto)))
        } else {
            emit(Resource.Loading)

            val response = networkClient.doRequest(Request.AreasRequest)

            when (response.resultCode) {
                ResultCode.SUCCESS -> {
                    areasDto = response.data as? AreasDto
                    emit(Resource.Success(mapContries(areasDto)))
                }

                else -> emit(Resource.Error(response.resultCode))
            }
        }
    }

    private fun mapContries(dto: AreasDto?): List<Area> {
        return dto?.map { Area(id = it.id, name = it.name) } ?: emptyList()
    }

}
