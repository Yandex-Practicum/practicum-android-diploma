package ru.practicum.android.diploma.similars.domain.api

import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure

interface GetSimilarVacanciesUseCase {
    suspend operator fun invoke(id: String): Either<Failure, List<Vacancy>>
}