package ru.practicum.android.diploma.search.domain.impl

import ru.practicum.android.diploma.search.domain.api.RemoteRepository
import ru.practicum.android.diploma.search.domain.api.SearchVacanciesUseCase
import javax.inject.Inject

class SearchVacanciesUseCaseImpl @Inject constructor(private val repository: RemoteRepository):
    SearchVacanciesUseCase {
    override suspend fun search(query: String) {
        repository.search(query)
    }
}