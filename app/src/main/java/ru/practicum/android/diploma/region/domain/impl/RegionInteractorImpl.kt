package ru.practicum.android.diploma.region.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.Resource
import ru.practicum.android.diploma.region.domain.api.RegionInteractor
import ru.practicum.android.diploma.region.domain.api.RegionRepository
import ru.practicum.android.diploma.region.domain.models.RegionItem

class RegionInteractorImpl(
    private val regionRepository: RegionRepository
) : RegionInteractor {
    override fun getRegions(countryId: String?): Flow<Resource<List<RegionItem>>> =
        regionRepository.getRegions(countryId)
}
