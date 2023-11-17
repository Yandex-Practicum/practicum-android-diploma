package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface SearchInteractor {
    fun loadVacancies(query: String, pageCount: Int): Flow<Pair<List<Vacancy>?, String?>>
    fun checkFilters():Boolean
}