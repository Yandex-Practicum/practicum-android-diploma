package ru.practicum.android.diploma.domain.vacancy.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.network.ApiResponse
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.vacancy.api.VacancyInteractor
import ru.practicum.android.diploma.domain.vacancy.api.VacancyRepository

class VacancyInteractorImpl(
    private val vacancyRepository: VacancyRepository
) : VacancyInteractor {

    override fun execute(id: String): Flow<Vacancy?> {
        return vacancyRepository.getVacancy(id).map { result ->
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
