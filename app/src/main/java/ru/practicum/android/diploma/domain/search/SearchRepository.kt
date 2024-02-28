package ru.practicum.android.diploma.domain.search

import ru.practicum.android.diploma.data.search.network.Resource
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyData

interface SearchRepository {
    suspend fun search(
        expression: String,
        page: Int,
    ): Resource<VacancyData>
}
