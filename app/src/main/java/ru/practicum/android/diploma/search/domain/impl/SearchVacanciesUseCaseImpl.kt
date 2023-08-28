package ru.practicum.android.diploma.search.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.practicum.android.diploma.search.domain.api.RemoteRepository
import ru.practicum.android.diploma.search.domain.api.SearchVacanciesUseCase
import ru.practicum.android.diploma.search.domain.models.FetchResult
import javax.inject.Inject

class SearchVacanciesUseCaseImpl @Inject constructor(
    private val repository: RemoteRepository
):
    SearchVacanciesUseCase {
    
    override suspend fun search(query: String): Flow<FetchResult> {
       return repository.search(query)
    }
}