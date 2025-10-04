package ru.practicum.android.diploma.domain.usecase

import ru.practicum.android.diploma.domain.models.SearchResult
import ru.practicum.android.diploma.domain.repository.VacancyRepository
import ru.practicum.android.diploma.util.Resource

class SearchVacanciesUseCase(private val repository: VacancyRepository) {
    suspend operator fun invoke(query: String, page: Int): Resource<SearchResult> {
        return repository.searchVacancies(query, page)
    }
}
