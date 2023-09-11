package ru.practicum.android.diploma.search.domain.api

import ru.practicum.android.diploma.search.domain.models.Vacancies
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure

interface SearchVacanciesUseCase {
    suspend operator fun invoke(query: String, page: Int): Either<Failure, Vacancies>
}