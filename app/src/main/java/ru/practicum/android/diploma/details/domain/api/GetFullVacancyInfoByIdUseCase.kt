package ru.practicum.android.diploma.details.domain.api

import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure

interface GetFullVacancyInfoByIdUseCase {
    suspend operator fun invoke(id: String): Either<Failure, VacancyFullInfo>
}