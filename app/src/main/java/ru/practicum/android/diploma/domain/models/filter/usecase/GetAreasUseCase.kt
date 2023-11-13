package ru.practicum.android.diploma.domain.models.filter.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.api.DirectoryRepository
import ru.practicum.android.diploma.domain.models.filter.Area
import ru.practicum.android.diploma.util.DataResponse
import ru.practicum.android.diploma.util.Resource



class GetAreasUseCase(private val directoryRepository: DirectoryRepository) {

    operator fun invoke(areaId: String): Flow<DataResponse<Area>> {
        return directoryRepository.getAreas(areaId).map { result ->

            when(result) {
                is Resource.Success -> {
                    DataResponse(data = result.data, networkError = null)
                }

                is Resource.Error -> {
                    DataResponse(data = null, networkError = result.message)
                }
            }

        }
    }

}