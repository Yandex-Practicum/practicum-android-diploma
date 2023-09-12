package ru.practicum.android.diploma.search.domain.api

import ru.practicum.android.diploma.di.annotations.NewResponse
import ru.practicum.android.diploma.search.domain.models.Vacancies
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure

interface SearchRepository {
    @NewResponse
    suspend fun searchVacancies(queryParams: Map<String, String>): Either<Failure, Vacancies>
}