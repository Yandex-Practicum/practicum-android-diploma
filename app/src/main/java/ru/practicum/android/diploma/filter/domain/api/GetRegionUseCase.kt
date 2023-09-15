package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure

interface GetRegionUseCase {
    suspend fun getAllRegions() : Either<Failure, List<Region>>
    suspend fun getRegions(id: String) : Either<Failure, List<Region>>
}