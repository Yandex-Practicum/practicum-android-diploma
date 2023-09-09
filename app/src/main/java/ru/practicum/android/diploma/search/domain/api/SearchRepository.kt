package ru.practicum.android.diploma.search.domain.api

import ru.practicum.android.diploma.search.domain.models.Vacancies
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure

interface SearchRepository {

    suspend fun searchVacancies(query: String): Either<Failure, Vacancies>
}