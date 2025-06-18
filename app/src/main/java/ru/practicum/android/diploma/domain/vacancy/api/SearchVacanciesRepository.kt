package ru.practicum.android.diploma.domain.vacancy.api

import ru.practicum.android.diploma.data.network.ApiResponse
import ru.practicum.android.diploma.domain.models.FilterOptions
import ru.practicum.android.diploma.domain.vacancy.models.Vacancy

interface SearchVacanciesRepository {
    suspend fun search(options: FilterOptions): ApiResponse<List<Vacancy>>
}
