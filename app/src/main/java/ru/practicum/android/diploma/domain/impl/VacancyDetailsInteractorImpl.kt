package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.models.VacancyDetailsSearchResult

class VacancyDetailsInteractorImpl(
    private val repository: VacancyDetailsRepository
) : VacancyDetailsInteractor {
    override suspend fun getVacancyDetails(id: String): VacancyDetailsSearchResult =
        repository.getVacancyDetails(id).let { result ->
            if (result.data != null) result
            else VacancyDetailsSearchResult(null, result.code)
        }
}
