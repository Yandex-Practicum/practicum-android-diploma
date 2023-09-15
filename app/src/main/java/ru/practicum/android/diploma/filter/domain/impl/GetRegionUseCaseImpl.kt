package ru.practicum.android.diploma.filter.domain.impl

import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.domain.api.GetRegionUseCase
import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure
import javax.inject.Inject

class GetRegionUseCaseImpl @Inject constructor(
    private val repository: FilterRepository
) : GetRegionUseCase{
    
    override suspend fun getAllRegions(): Either<Failure, List<Region>> {
        return repository.getAllRegions()
    }

    override suspend fun getRegions(id: String): Either<Failure, List<Region>> {
        return repository.searchRegions(id = id)
    }
}