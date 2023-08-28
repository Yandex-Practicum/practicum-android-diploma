package ru.practicum.android.diploma.search.domain.api

interface SearchVacanciesUseCase {
    suspend fun search(query: String)
}