package ru.practicum.android.diploma.details.domain

import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure

interface DetailsInteractor {
   suspend fun getFullVacancyInfoById(id: String): Either<Failure, VacancyFullInfo>
   suspend fun getSimilarVacancies(id: String): Either<Failure, List<Vacancy>>
}