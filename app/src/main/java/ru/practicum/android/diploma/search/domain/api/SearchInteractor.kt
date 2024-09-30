package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.Resource
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.models.VacancySearchParams

interface SearchInteractor {
    suspend fun searchVacancies(params: VacancySearchParams):
        Flow<Resource<List<Vacancy>>>
}
