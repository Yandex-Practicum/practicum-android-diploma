package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.di.annotations.NewResponse
import ru.practicum.android.diploma.filter.domain.models.NetworkResponse
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.search.domain.models.FetchResult
import ru.practicum.android.diploma.search.domain.models.Vacancies
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure

interface SearchRepository {

    @NewResponse
    suspend fun searchVacancies(query: String): Either<Failure, Vacancies>
    suspend fun search(query: String): Flow<FetchResult>

    suspend fun getCountries(): Flow<NetworkResponse<List<Country>>>
    suspend fun getRegionsById(countryId: String): Flow<NetworkResponse<List<Region>>>
}