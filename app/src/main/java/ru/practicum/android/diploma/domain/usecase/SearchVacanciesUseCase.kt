package ru.practicum.android.diploma.domain.usecase

import ru.practicum.android.diploma.domain.models.SearchResult
import ru.practicum.android.diploma.domain.repository.DataRepository
import ru.practicum.android.diploma.util.Resource

class SearchVacanciesUseCase(
    private val repository: DataRepository
) {
    suspend fun execute(query: String, page: Int): Resource<SearchResult> {
        return repository.searchVacancies(query, page)
    }
}
