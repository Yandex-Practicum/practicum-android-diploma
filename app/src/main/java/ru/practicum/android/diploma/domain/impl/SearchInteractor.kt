package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.models.VacanciesSearchResult

class SearchInteractor(
    private val vacanciesRepository: VacanciesRepository,
) {
    suspend fun searchVacancies(query: String): VacanciesSearchResult? {
        val trimmedQuery = query.trim()
        if (trimmedQuery.isEmpty()) {
            return null
        }
        return vacanciesRepository.searchVacancies(trimmedQuery)
    }
}
