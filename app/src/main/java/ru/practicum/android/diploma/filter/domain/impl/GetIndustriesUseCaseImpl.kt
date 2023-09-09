package ru.practicum.android.diploma.filter.domain.impl

import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.domain.api.GetIndustriesUseCase
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure
import javax.inject.Inject

class GetIndustriesUseCaseImpl @Inject constructor(private val repository: FilterRepository) :
    GetIndustriesUseCase {
    override suspend fun invoke(): Either<Failure, List<Industry>> {
        return repository.getIndustries()
    }
}