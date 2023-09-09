package ru.practicum.android.diploma.filter.domain.impl

import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.domain.api.GetAllCountriesUseCase
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure
import javax.inject.Inject

class GetAllCountriesUseCaseImpl @Inject constructor(
    private val repository: FilterRepository
): GetAllCountriesUseCase {

    override suspend fun invoke(): Either<Failure, List<Country>> {
        return repository.getAllCountries()
    }
}