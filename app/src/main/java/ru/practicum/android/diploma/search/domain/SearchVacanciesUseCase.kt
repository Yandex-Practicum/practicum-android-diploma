package ru.practicum.android.diploma.search.domain

interface SearchVacanciesUseCase {
    suspend fun search(query: String)
}