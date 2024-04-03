package ru.practicum.android.diploma.domain.filter

import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.domain.filter.datashared.IndustriesShared

interface FilterRepositoryIndustriesFlow {

    fun setIndustriesFlow(industries: IndustriesShared?)

    fun getIndustriesFlow(): StateFlow<IndustriesShared?>
}
