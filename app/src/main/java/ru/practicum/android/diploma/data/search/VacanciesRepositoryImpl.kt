package ru.practicum.android.diploma.data.search

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.converters.VacanciesConverter
import ru.practicum.android.diploma.data.dto.VacanciesResponseDto
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.search.api.VacanciesRepository

class VacanciesRepositoryImpl(
    private val vacanciesConverter: VacanciesConverter,
    private val networkClient: NetworkClient,
) : VacanciesRepository {

    companion object {
        private const val NO_INTERNET_CONNECTION = -1
        private const val SUCCESSFUL_RESPONSE = 200
    }

    override fun searchVacancies(): Flow<Resource<List<Vacancy>>> = flow {
        val response = networkClient.doRequestVacancies()

        when (response.resultCode) {
            NO_INTERNET_CONNECTION -> emit(Resource.Error("No internet connection"))

            SUCCESSFUL_RESPONSE -> {
                with(response as VacanciesResponseDto) {
                    val data = vacanciesConverter.convertCut(response)
                    emit(Resource.Success(data))
                }
            }
        }
    }
}
