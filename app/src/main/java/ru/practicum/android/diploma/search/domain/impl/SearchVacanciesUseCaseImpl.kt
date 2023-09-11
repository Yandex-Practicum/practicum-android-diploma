package ru.practicum.android.diploma.search.domain.impl

import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.api.SearchVacanciesUseCase
import ru.practicum.android.diploma.search.domain.models.Vacancies
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure
import javax.inject.Inject

class SearchVacanciesUseCaseImpl @Inject constructor(
    private val repository: SearchRepository,
) : SearchVacanciesUseCase {
    override suspend fun invoke(query: String, page: Int): Either<Failure, Vacancies> {
        return repository.searchVacancies(query, page.toString())
    }
}