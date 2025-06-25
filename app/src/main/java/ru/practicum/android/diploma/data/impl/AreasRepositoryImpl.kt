package ru.practicum.android.diploma.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.AreasDto
import ru.practicum.android.diploma.data.filters.AreasRequest
import ru.practicum.android.diploma.data.filters.AreasResponse
import ru.practicum.android.diploma.data.network.NetworkClientInterface
import ru.practicum.android.diploma.domain.filters.AreasRepository
import ru.practicum.android.diploma.domain.models.Areas
import ru.practicum.android.diploma.util.HTTP_200_OK
import ru.practicum.android.diploma.util.HTTP_500_INTERNAL_SERVER_ERROR
import ru.practicum.android.diploma.util.HTTP_NO_CONNECTION
import ru.practicum.android.diploma.util.Resource

class AreasRepositoryImpl(
    private val network: NetworkClientInterface
) : AreasRepository {
    override fun getAreas(): Flow<Resource<List<Areas>>> = flow {
        val response = network.doRequest(AreasRequest())
        when (response.resultCode) {
            HTTP_NO_CONNECTION -> emit(Resource.Error(HTTP_NO_CONNECTION))
            HTTP_200_OK -> {
                with(response as AreasResponse) {
                    val data = areas.map {
                        dtoToModel(it)
                    }
                    emit(Resource.Success(data))
                }
            }

            else -> emit(Resource.Error(HTTP_500_INTERNAL_SERVER_ERROR))
        }
    }

    private fun dtoToModel(dto: AreasDto): Areas {
        return Areas(
            id = dto.id,
            name = dto.name,
            parentId = dto.parentId,
            areas = if (dto.areas.isEmpty()) {
                emptyList<Areas>()
            } else {
                dto.areas.sortedBy { it.name }.map {
                    dtoToModel(it)
                }
            }
        )
    }
}
