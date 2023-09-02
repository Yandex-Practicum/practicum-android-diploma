package ru.practicum.android.diploma.features.search.domain.repository

import ru.practicum.android.diploma.features.filters.domain.models.Filter
import ru.practicum.android.diploma.features.search.domain.model.ResponseModel

interface SearchVacancyRepository {
    suspend fun loadVacancies(value: String, filterParams: Filter): ResponseModel
}