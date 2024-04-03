package ru.practicum.android.diploma.domain.filter.impl

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.data.filter.storage.impl.FiltersLocalStorage
import ru.practicum.android.diploma.domain.filter.FilterRepositoryIndustriesFlow
import ru.practicum.android.diploma.domain.filter.datashared.IndustriesShared

class FilterRepositoryIndustriesFlowImpl(
    private val sharedPreferences: FiltersLocalStorage
) : FilterRepositoryIndustriesFlow {

    private val industriesFlow: MutableStateFlow<IndustriesShared?> =
        MutableStateFlow(sharedPreferences.loadIndustriesState())

    override fun setIndustriesFlow(industries: IndustriesShared?) {
        industriesFlow.value = industries
        sharedPreferences.saveIndustriesState(industries)
    }

    override fun getIndustriesFlow(): StateFlow<IndustriesShared?> = industriesFlow
}
