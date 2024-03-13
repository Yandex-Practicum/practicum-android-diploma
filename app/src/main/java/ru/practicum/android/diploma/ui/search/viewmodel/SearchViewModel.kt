package ru.practicum.android.diploma.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.filters.FiltersRepository
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.filter.FilterInfoRepository
import ru.practicum.android.diploma.domain.filter.datashared.CountryShared
import ru.practicum.android.diploma.domain.filter.datashared.IndustriesShared
import ru.practicum.android.diploma.domain.filter.datashared.RegionShared
import ru.practicum.android.diploma.domain.filter.datashared.SalaryBooleanShared
import ru.practicum.android.diploma.domain.filter.datashared.SalaryTextShared
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.search.VacanciesPagingSource
import ru.practicum.android.diploma.ui.search.SearchState
import ru.practicum.android.diploma.ui.search.SearchViewState
import ru.practicum.android.diploma.util.Resource

class SearchViewModel(
    private val repository: SearchRepository,
    private val filtersRepository: FiltersRepository,
    private val filterInfoRepository : FilterInfoRepository
) : ViewModel() {

    private val _countryState = MutableLiveData<CountryShared?>()
    val countryState: LiveData<CountryShared?> = _countryState

    private val _regionState = MutableLiveData<RegionShared?>()
    val regionState: LiveData<RegionShared?> = _regionState

    private val _industriesState = MutableLiveData<IndustriesShared?>()
    val industriesState: LiveData<IndustriesShared?> = _industriesState

    private val _salaryTextState = MutableLiveData<SalaryTextShared?>()
    val salaryTextState: LiveData<SalaryTextShared?> = _salaryTextState

    private val _salaryBooleanState = MutableLiveData<SalaryBooleanShared?>()
    val salaryBooleanState: LiveData<SalaryBooleanShared?> = _salaryBooleanState

    init {
        initFilterInfo()
    }

    fun initFilterInfo() {
        initCountryInfo()
        initRegionInfo()
        initIndustriesInfo()
        initSalaryTextInfo()
        initSalaryBooleanInfo()
    }

    fun initCountryInfo() {
        viewModelScope.launch {
            filterInfoRepository.getCountryFlow()
                .collect() { country ->
                    _countryState.postValue(country)
                }
        }
    }

    fun initRegionInfo() {
        viewModelScope.launch {
            filterInfoRepository.getRegionFlow()
                .collect() { region ->
                    _regionState.postValue(region)
                }
        }
    }

    fun initIndustriesInfo() {
        viewModelScope.launch {
            filterInfoRepository.getIndustriesFlow()
                .collect() { industries ->
                    _industriesState.postValue(industries)
                }
        }
    }

    fun initSalaryTextInfo() {
        viewModelScope.launch {
            filterInfoRepository.getSalaryTextFlow()
                .collect() { salary ->
                    _salaryTextState.postValue(salary)
                }

        }
    }

    fun initSalaryBooleanInfo() {
        viewModelScope.launch {
            filterInfoRepository.getSalaryBooleanFlow()
                .collect() { salary ->
                    _salaryBooleanState.postValue(salary)
                }

        }
    }

    private val state = MutableStateFlow(SearchViewState())
    fun observeState() = state.asStateFlow()
    var flow: Flow<PagingData<Vacancy>> = emptyFlow()
        private set

    private var query: String = ""

    private fun subscribeVacanciesPagination(params: Map<String, String>) {
        flow = Pager(PagingConfig(pageSize = 20)) {
            VacanciesPagingSource(repository, params, null, null)
        }.flow.cachedIn(viewModelScope)
        state.update { it.copy(state = SearchState.Content(emptyList())) }
    }

    fun onSearch(text: String) {
        query = text
        if (text.isEmpty()) {
            state.update { it.copy(state = null) }
            return
        }

        state.update { it.copy(state = SearchState.Loading) }
        viewModelScope.launch {
            searchRequest()
        }
    }

    private fun searchRequest() {
        if (query.isNotEmpty()) {
            viewModelScope.launch {
                state.update { it.copy(state = SearchState.Loading) }
                val filter = filtersRepository.getFilters()

                val params = mutableMapOf("text" to query)
                params["page"] = "1"

                filter.salary?.let {
                    params["salary"] = salaryTextState.value?.salary.toString()
                }

                filter.onlyWithSalary?.let {
                    params["only_with_salary"] = salaryBooleanState.value?.isChecked.toString()
                }

                filter.country?.let {
                    params["area"] = countryState.value?.countryId.toString()
                }

                filter.region?.let {
                    params["area"] = regionState.value?.regionId.toString()
                }

                filter.industry?.let {
                    params["industry"] = industriesState.value?.industriesId.toString()
                }

                when (val result = repository.vacanciesPagination(params)) {
                    is Resource.Success -> {
                        val founded = result.data?.foundedVacancies?.toString()?.let {
                            if (it != "0") {
                                "Найдено $it вакансий"
                            } else {
                                "Таких вакансий нет"
                            }
                        } ?: "Таких вакансий нет"

                        state.update { it.copy(foundVacancies = founded) }
                        subscribeVacanciesPagination(params)
                        if (founded == "Таких вакансий нет") {
                            state.update { it.copy(state = SearchState.Empty) }
                        }
                    }

                    is Resource.Error -> {
                        state.update { it.copy(state = SearchState.Error) }
                    }
                    is Resource.ServerError -> {
                        state.update { it.copy(state = SearchState.ServerError) }
                    }
                }
            }
        } else {
            state.update { it.copy(state = SearchState.Empty) }
        }
    }

    fun updatesFilters(gson: String) {
        val filter = Gson().fromJson(gson, Filter::class.java)
        filtersRepository.setFilter(filter)
        searchRequest()
    }
}
