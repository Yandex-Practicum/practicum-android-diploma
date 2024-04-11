package ru.practicum.android.diploma.domain.filter.impl

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filter.FilterRepositoryCountryFlow
import ru.practicum.android.diploma.domain.filter.FilterRepositoryIndustriesFlow
import ru.practicum.android.diploma.domain.filter.FilterRepositoryRegionFlow
import ru.practicum.android.diploma.domain.filter.FilterRepositorySalaryBooleanFlow
import ru.practicum.android.diploma.domain.filter.FilterRepositorySalaryTextFlow
import ru.practicum.android.diploma.domain.filter.FiltersRepository
import ru.practicum.android.diploma.domain.models.Filters

class FiltersRepositoryImpl(
    private val filterRepositoryCountryFlow: FilterRepositoryCountryFlow,
    private val filterRepositoryRegionFlow: FilterRepositoryRegionFlow,
    private val filterRepositoryIndustriesFlow: FilterRepositoryIndustriesFlow,
    private val filterRepositorySalaryTextFlow: FilterRepositorySalaryTextFlow,
    private val filterRepositorySalaryBooleanFlow: FilterRepositorySalaryBooleanFlow
) : FiltersRepository {

    private var job: Job? = null
    private val filtersFlow: MutableStateFlow<Filters> = MutableStateFlow(Filters())

    override fun setFilters() {
        job = CoroutineScope(Dispatchers.IO).launch {
            filterRepositoryCountryFlow.getCountryFlow().collect { county ->
                filterRepositoryRegionFlow.getRegionFlow().collect { region ->
                    filterRepositoryIndustriesFlow.getIndustriesFlow().collect { industries ->
                        filterRepositorySalaryTextFlow.getSalaryTextFlow().collect { salaryText ->
                            filterRepositorySalaryBooleanFlow.getSalaryBooleanFlow().collect { salaryBoolean ->
                                var area = county?.countryId
                                if (region != null) {
                                    area = region.regionId
                                }
                                filtersFlow.value = Filters(
                                    area = area,
                                    industry = industries?.industriesId,
                                    salary = salaryText?.salary,
                                    salaryBoolean = if (salaryBoolean != null) "true" else null,
                                )
                                Log.d("filters source", filtersFlow.value.toString())
                            }
                        }
                    }
                }
            }
        }
    }

    override fun getFiltersFlow(): Flow<Filters> {
        setFilters()
        return filtersFlow
    }

    override fun cancelJob() {
        job?.cancel()
    }

}
