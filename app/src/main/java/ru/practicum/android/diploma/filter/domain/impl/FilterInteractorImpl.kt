package ru.practicum.android.diploma.filter.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import javax.inject.Inject

class FilterInteractorImpl @Inject constructor(
    val filterRepository: FilterRepository,
    val searchRepository: SearchRepository,
    val logger: Logger
) :
    FilterInteractor {
    override fun filter() {

    }
    override suspend fun getCountries(): Flow<List<Country>> {
        return searchRepository.getCountries()
    }
}