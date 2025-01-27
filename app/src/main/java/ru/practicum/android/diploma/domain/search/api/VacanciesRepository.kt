package ru.practicum.android.diploma.domain.search.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.models.Vacancy

interface VacanciesRepository {
    fun searchVacancies(text: String?): Flow<Resource<List<Vacancy>>>
}
