package ru.practicum.android.diploma.domain.interactors

import ru.practicum.android.diploma.domain.models.VacancySearchRequest
import ru.practicum.android.diploma.domain.models.VacancySearchResult
import ru.practicum.android.diploma.util.Resource

interface VacanciesInteractor {
    suspend fun searchVacancies(request: VacancySearchRequest): Resource<VacancySearchResult>
}
