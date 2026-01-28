package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface SearchVacanciesRepository {
    suspend fun searchVacancies(expression: String): Flow<SearchVacanciesInteractor.Resource<List<Vacancy>>>
}
