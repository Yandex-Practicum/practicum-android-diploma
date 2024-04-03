package ru.practicum.android.diploma.domain.filter

import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.domain.filter.datashared.RegionShared

interface FilterRepositoryRegionFlow {

    fun setRegionFlow(region: RegionShared?)

    fun getRegionFlow(): StateFlow<RegionShared?>
}
