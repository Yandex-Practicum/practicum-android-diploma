package ru.practicum.android.diploma.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.search.SearchPagingRepository
import ru.practicum.android.diploma.domain.api.search.VacanciesSearchRepository
import ru.practicum.android.diploma.domain.debugLog
import ru.practicum.android.diploma.domain.filter.FilterRepositoryCountryFlow
import ru.practicum.android.diploma.domain.filter.FilterRepositoryIndustriesFlow
import ru.practicum.android.diploma.domain.filter.FilterRepositoryRegionFlow
import ru.practicum.android.diploma.domain.filter.FilterRepositorySalaryBooleanFlow
import ru.practicum.android.diploma.domain.filter.FilterRepositorySalaryTextFlow
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

    init {
        with(viewModelScope) {
            launch {
                filterRepositoryCountryFlow.getCountryFlow()
                    .collect { country ->
                        Log.e("CountryFlow", country.toString())
                    }
            }

            launch {
                filterRepositoryRegionFlow.getRegionFlow()
                    .collect { region ->
                        Log.e("regionFlow", region.toString())
                    }
            }

            launch {
                filterRepositoryIndustriesFlow.getIndustriesFlow()
                    .collect { industries ->
                        Log.e("industriesFlow", industries.toString())
                    }
            }

            launch {
                filterRepositorySalaryTextFlow.getSalaryTextFlow()
                    .collect { salarySum ->
                        Log.e("salarySumFlow", salarySum.toString())
                    }
            }

            launch {
                filterRepositorySalaryBooleanFlow.getSalaryBooleanFlow()
                    .collect { salaryBoolean ->
                        Log.e("salaryBooleanFlow", salaryBoolean.toString())
                    }
            }
        }
    }

    fun search(text: String): Flow<PagingData<Vacancy>> {
        viewModelScope.launch {
            vacancySearchRepository.getVacancies(text, 1).collect {
                if (it.first != null) {
                    Log.d("searchFound", (it.first as VacancyResponse).found.toString())
                    foundLiveData.postValue((it.first as VacancyResponse).found)
                }
            }
        }
        Log.d("searchPaging", text)
        return searchPagingRepository.getSearchPaging(text).cachedIn(viewModelScope)
    }

    fun clearFoundLiveData() {
        foundLiveData.value = -1
    }
}
