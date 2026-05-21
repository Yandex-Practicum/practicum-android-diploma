package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.models.SearchVacanciesOutcome

class SearchInteractor(
    private val vacanciesRepository: VacanciesRepository,
) {
    suspend fun searchVacancies(query: String, page: Int = 0): SearchVacanciesOutcome {
        val trimmedQuery = query.trim()
        if (trimmedQuery.isEmpty()) {
            return SearchVacanciesOutcome.Error
        }
        return vacanciesRepository.searchVacancies(trimmedQuery, page)
    }
}
