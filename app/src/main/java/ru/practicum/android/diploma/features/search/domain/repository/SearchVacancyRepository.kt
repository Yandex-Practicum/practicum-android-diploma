package ru.practicum.android.diploma.features.search.domain.repository

import ru.practicum.android.diploma.features.search.domain.model.Filter
import ru.practicum.android.diploma.features.search.domain.model.ResponseModel

interface SearchVacancyRepository {
    suspend fun loadVacancies(value: String, filterParams: Filter): ResponseModel
}