package ru.practicum.android.diploma.filter.area.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.core.data.NetworkClient
import ru.practicum.android.diploma.core.data.mapper.mapToDomain
import ru.practicum.android.diploma.core.data.network.dto.AreasDto
import ru.practicum.android.diploma.core.data.network.dto.GetAreasResponse
import ru.practicum.android.diploma.core.data.network.dto.Response
import ru.practicum.android.diploma.filter.area.domain.api.AreaRepository
import ru.practicum.android.diploma.filter.area.domain.model.Area
import ru.practicum.android.diploma.filter.area.domain.model.AreaError
import ru.practicum.android.diploma.util.Result

class AreaRepositoryImpl(
    private val networkClient: NetworkClient
) : AreaRepository {
    override fun getAreas(id: String): Flow<Result<List<Area>, AreaError>> = flow {
        val response: Response
        response = networkClient.getAreas()

        when (response.resultCode) {
            NetworkClient.SUCCESSFUL_CODE -> {
                var data = (response as GetAreasResponse).areas

                if (!id.isNullOrEmpty()) {
                    for (area in data) {
                        if (area.id.equals(id)) {
                            data = listOf(area)
                            break
                        }
                    }
                }
                data = unCoverList(data)
                if (!id.isNullOrEmpty()) {
                    data = data.filter { !it.parentId.isNullOrEmpty() }
                }
                emit(Result.Success(data.mapToDomain()))
            }

            else -> {
                emit(Result.Error(AreaError.GetError))
            }
        }
    }

    private fun unCoverList(data: List<AreasDto>): List<AreasDto> {
        var newData = data
        while (isExistNested(data)) {
            newData = newData.flatMap {
                if (it.areas.isNullOrEmpty()) {
                    listOf(it)
                } else {
                    it.areas ?: emptyList()
                }
            }
        }
        return newData
    }

    private fun isExistNested(list: List<AreasDto>): Boolean {
        list.forEach {
            if (!it.areas.isNullOrEmpty()) {
                return true
            }
        }
        return false
    }
}
