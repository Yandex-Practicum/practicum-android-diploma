package ru.practicum.android.diploma.domain.interactors.impl

import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.interactors.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.VacancySearchRequest
import ru.practicum.android.diploma.domain.models.VacancySearchResult
import ru.practicum.android.diploma.util.Resource

class VacanciesInteractorImpl(
    private val repository: VacanciesRepository,
) : VacanciesInteractor {

    override suspend fun searchVacancies(request: VacancySearchRequest): Resource<VacancySearchResult> {
        return repository.searchVacancies(request)
    }
}
