package ru.practicum.android.diploma.details.domain

import ru.practicum.android.diploma.details.domain.models.VacancyDetails
import ru.practicum.android.diploma.search.domain.SearchRepository
import ru.practicum.android.diploma.util.Resource

class VacancyInteractorImpl(
    private val repository: SearchRepository
): VacancyInteractor {

    override suspend fun loadVacancyDetails(vacancyId: String): Pair<VacancyDetails?, String?> {
        return when(val result = repository.loadVacancyDetails(vacancyId)){
            is Resource.Success -> {
                Pair(result.data, null)
            }

            is Resource.Error -> {
                Pair(null, result.message)
            }
        }
    }
}