package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.model.Resource
import ru.practicum.android.diploma.search.domain.model.VacancyPreview

interface SearchRepository {
    fun getVacancies(text: String, page: Int, filters: Map<String, String?>): Flow<Resource<List<VacancyPreview>>>
}
