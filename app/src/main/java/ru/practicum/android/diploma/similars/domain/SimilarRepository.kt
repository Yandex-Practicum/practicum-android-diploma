package ru.practicum.android.diploma.similars.domain

import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure

interface SimilarRepository {
    suspend fun getSimilarVacancies(id: String): Either<Failure, List<Vacancy>>
}