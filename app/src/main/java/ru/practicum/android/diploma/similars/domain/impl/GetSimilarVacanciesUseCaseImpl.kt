package ru.practicum.android.diploma.similars.domain.impl

import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.similars.domain.SimilarRepository
import ru.practicum.android.diploma.similars.domain.api.GetSimilarVacanciesUseCase
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure
import javax.inject.Inject

class GetSimilarVacanciesUseCaseImpl @Inject constructor(private val repository: SimilarRepository) :
    GetSimilarVacanciesUseCase {
    override suspend operator fun invoke(id: String): Either<Failure, List<Vacancy>> {
        return repository.getSimilarVacancies(id)
    }
}