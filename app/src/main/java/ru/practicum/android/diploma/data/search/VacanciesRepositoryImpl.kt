package ru.practicum.android.diploma.data.search

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converters.VacanciesConverter
import ru.practicum.android.diploma.data.network.ApiResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.models.VacanciesResponse
import ru.practicum.android.diploma.domain.search.api.VacanciesRepository

class VacanciesRepositoryImpl(
    private val vacanciesConverter: VacanciesConverter,
    private val networkClient: NetworkClient,
) : VacanciesRepository {

    override fun getVacancies(): Flow<ApiResponse<VacanciesResponse>> = flow {
        when (val response = networkClient.doRequestVacancies()) {
            null -> {
                emit(ApiResponse.Error())
            }

            else -> {
                val data = vacanciesConverter.convertCut(response)
                emit(ApiResponse.Success(data = data))
            }
        }
    }
}
