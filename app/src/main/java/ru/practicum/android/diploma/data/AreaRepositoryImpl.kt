package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.AreasRequest
import ru.practicum.android.diploma.data.dto.FilterAreaDto
import ru.practicum.android.diploma.domain.api.AreaRepository
import ru.practicum.android.diploma.domain.models.AreaResult

class AreaRepositoryImpl(
    private val networkClient: NetworkClient,
) : AreaRepository {

    override suspend fun getAreas(): AreaResult {
        val response = networkClient.doRequest(AreasRequest)
        return when (response.resultCode) {
            NO_INTERNET -> AreaResult.NoInternet
            HTTP_SERVER_ERROR -> AreaResult.ServerError
            HTTP_OK -> {
                val data = response.data as? List<FilterAreaDto>
                if (data == null) {
                    AreaResult.Empty
                } else {
                    AreaResult.Success(data.toDomain())
                }
            }
            else -> AreaResult.Error
        }
    }

    private companion object {
        const val HTTP_OK = 200
        const val NO_INTERNET = -1
        const val HTTP_SERVER_ERROR = 500
    }
}
