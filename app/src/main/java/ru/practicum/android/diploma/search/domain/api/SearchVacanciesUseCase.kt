package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.di.annotations.NewResponse
import ru.practicum.android.diploma.search.domain.models.FetchResult
import ru.practicum.android.diploma.search.domain.models.Vacancies
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure

interface SearchVacanciesUseCase {
    suspend fun search(query: String): Flow<FetchResult>

    @NewResponse
    suspend fun searchVacancies(query: String): Either<Failure, Vacancies>
}