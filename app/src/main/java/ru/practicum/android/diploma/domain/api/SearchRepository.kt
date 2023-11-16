package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.filter.Filters
import ru.practicum.android.diploma.util.Resource

interface SearchRepository {
    fun searchVacancies(query: String, filters: Filters): Flow<Resource<List<Vacancy>>>
}