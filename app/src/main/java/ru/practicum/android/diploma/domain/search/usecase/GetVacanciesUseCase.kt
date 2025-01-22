package ru.practicum.android.diploma.domain.search.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.network.ApiResponse
import ru.practicum.android.diploma.domain.models.VacanciesResponse
import ru.practicum.android.diploma.domain.search.api.VacanciesRepository

class GetVacanciesUseCase(
    private val vacanciesRepository: VacanciesRepository
) {

    fun execute(): Flow<VacanciesResponse?> {
        return vacanciesRepository.getVacancies().map { result ->
            when (result) {
                is ApiResponse.Success -> {
                    result.data
                }

                is ApiResponse.Error -> {
                    null
                }
            }
        }
    }
}
