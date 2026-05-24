package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.VacancySearchRequest
import ru.practicum.android.diploma.domain.models.VacancySearchResult
import ru.practicum.android.diploma.util.Resource

interface VacanciesRepository {
    suspend fun searchVacancies(request: VacancySearchRequest): Resource<VacancySearchResult>
}
