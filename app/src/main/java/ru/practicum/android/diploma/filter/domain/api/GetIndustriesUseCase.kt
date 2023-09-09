package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure

interface GetIndustriesUseCase {
    suspend operator fun invoke() : Either<Failure, List<Industry>>
}