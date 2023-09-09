package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure

interface GetAllCountriesUseCase {
    suspend operator fun invoke() : Either<Failure, List<Country>>
}