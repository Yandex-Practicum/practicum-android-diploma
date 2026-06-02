package ru.practicum.android.diploma.region.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.core.domain.Resource
import ru.practicum.android.diploma.core.domain.repository.FiltersRepository
import ru.practicum.android.diploma.region.domain.api.RegionInteractor
import ru.practicum.android.diploma.region.domain.api.RegionRepository
import ru.practicum.android.diploma.region.domain.models.RegionItem

class RegionInteractorImpl(
    private val regionRepository: RegionRepository,
    private val filtersRepository: FiltersRepository
) : RegionInteractor {
    override fun getRegions(): Flow<Resource<List<RegionItem>>> = flow {
        val countryId = filtersRepository.tempFilters.first().country?.id
        regionRepository.getRegions(countryId).collect { emit(it) }
    }
}
