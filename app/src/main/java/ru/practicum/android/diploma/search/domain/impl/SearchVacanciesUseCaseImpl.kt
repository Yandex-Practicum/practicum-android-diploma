package ru.practicum.android.diploma.search.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.search.domain.api.RemoteRepository
import ru.practicum.android.diploma.search.domain.api.SearchVacanciesUseCase
import ru.practicum.android.diploma.search.domain.models.FetchResult
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class SearchVacanciesUseCaseImpl @Inject constructor(
    private val repository: RemoteRepository,
    private val logger: Logger,
):
    SearchVacanciesUseCase {
    
    override suspend fun search(query: String): Flow<FetchResult> {
        logger.log(thisName, "fun search($query: String): Flow<FetchResult>")
        return repository.search(query)
    }
}