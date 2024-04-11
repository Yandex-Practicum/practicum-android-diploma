package ru.practicum.android.diploma.ui.search

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.search.SearchPagingRepository
import ru.practicum.android.diploma.domain.api.search.VacanciesSearchRepository
import ru.practicum.android.diploma.domain.debugLog
import ru.practicum.android.diploma.domain.filter.FilterRepositoryCountryFlow
import ru.practicum.android.diploma.domain.filter.FilterRepositoryIndustriesFlow
import ru.practicum.android.diploma.domain.filter.FilterRepositoryRegionFlow
import ru.practicum.android.diploma.domain.filter.FilterRepositorySalaryBooleanFlow
import ru.practicum.android.diploma.domain.filter.FilterRepositorySalaryTextFlow
import ru.practicum.android.diploma.domain.models.Filters
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy
import ru.practicum.android.diploma.domain.models.vacacy.VacancyResponse
import ru.practicum.android.diploma.ui.filter.FilterAllViewModel

class SearchViewModel(
    private val vacancySearchRepository: VacanciesSearchRepository,
    private val searchPagingRepository: SearchPagingRepository,
    private val filterRepositoryCountryFlow: FilterRepositoryCountryFlow,
    private val filterRepositoryRegionFlow: FilterRepositoryRegionFlow,
    private val filterRepositoryIndustriesFlow: FilterRepositoryIndustriesFlow,
    private val filterRepositorySalaryTextFlow: FilterRepositorySalaryTextFlow,
    private val filterRepositorySalaryBooleanFlow: FilterRepositorySalaryBooleanFlow
) : ViewModel() {

    var isCrossPressed = false
    var lastQuery = ""
    private val foundLiveData = MutableLiveData<Int>()

    fun observeState(): LiveData<Int> = foundLiveData

    private var filters = Filters()

    fun updateFilters() {
        viewModelScope.launch {
            filterRepositoryCountryFlow.getCountryFlow().collect { county ->
                filterRepositoryRegionFlow.getRegionFlow().collect { region ->
                    filterRepositoryIndustriesFlow.getIndustriesFlow().collect { industries ->
                        filterRepositorySalaryTextFlow.getSalaryTextFlow().collect { salaryText ->
                            filterRepositorySalaryBooleanFlow.getSalaryBooleanFlow().collect { salaryBoolean ->
                                var area = county?.countryId
                                if (region != null) {
                                    area = region.regionId
                                }
                                filters = Filters(
                                    area = area,
                                    industry = industries?.industriesId,
                                    salary = salaryText?.salary,
                                    salaryBoolean = (salaryBoolean != null).toString(),
                                )
                                Log.d("filters", filters.toString())
                            }
                        }
                    }
                }
            }
        }
    }

    fun search(text: String): Flow<PagingData<Vacancy>> {
        //val filters = getAllFilters()
        viewModelScope.launch {
            vacancySearchRepository.getVacancies(text, 0, filters).collect {
                if (it.first != null) {
                    Log.d("searchFound", (it.first as VacancyResponse).found.toString())
                    foundLiveData.postValue((it.first as VacancyResponse).found)
                }
            }
        }
        Log.d("searchPaging", text)
        return searchPagingRepository.getSearchPaging(text, filters).cachedIn(viewModelScope)
    }

    fun clearFoundLiveData() {
        foundLiveData.value = -1
    }
}
