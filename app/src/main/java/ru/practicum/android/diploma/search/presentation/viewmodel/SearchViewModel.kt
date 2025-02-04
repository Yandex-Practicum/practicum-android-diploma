package ru.practicum.android.diploma.search.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.data.Mapper
import ru.practicum.android.diploma.common.sharedprefs.interactor.SharedPrefsInteractor
import ru.practicum.android.diploma.common.sharedprefs.models.Filter
import ru.practicum.android.diploma.common.util.Converter
import ru.practicum.android.diploma.common.util.debounce
import ru.practicum.android.diploma.search.domain.interactor.SearchInteractor
import ru.practicum.android.diploma.search.domain.model.AdapterState
import ru.practicum.android.diploma.search.domain.model.SearchViewState
import ru.practicum.android.diploma.search.presentation.items.ListItem

class SearchViewModel(
    private val searchInteractor: SearchInteractor,
    private val mapper: Mapper,
    private val sharedPrefsInteractor: SharedPrefsInteractor,
) : ViewModel() {

    private var currentPage: Int = 1
    private var maxPages: Int? = 0
    private var latestSearchQuery: String? = null
    private var vacancyList = mutableListOf<ListItem>()
    private val emptyVacancyList = emptyList<ListItem>()
    private var isNextPageLoading: Boolean = false
    private var isClickAllowed = true
    private var job: Job? = null

    private val debounceSearch = debounce<String>(
        SEARCH_DEBOUNCE_DELAY,
        viewModelScope,
        true
    ) { query ->
        searchVacancy(query)
    }

    private val setIsClickAllowed = debounce<Boolean>(
        CLICK_DEBOUNCE_DELAY,
        viewModelScope,
        useLastParam = false
    ) { isAllowed ->
        isClickAllowed = isAllowed
    }

    private val _showVacancyDetails = MutableLiveData<String?>()
    val showVacancyDetails: MutableLiveData<String?> get() = _showVacancyDetails

    private val stateLiveData = MutableLiveData<SearchViewState>()
    fun observeState(): LiveData<SearchViewState> = stateLiveData

    private val filterStateLiveData = MutableLiveData<Boolean>()
    fun observeFilterStateLiveData(): LiveData<Boolean> = filterStateLiveData

    private val adapterStateLiveData = MutableLiveData<AdapterState>()
    fun getAdapterStateLiveData(): LiveData<AdapterState> = adapterStateLiveData

    fun searchDebounce(changedText: String) {
        if (latestSearchQuery == changedText) {
            return
        }
        this.latestSearchQuery = changedText
        debounceSearch(changedText)
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            setIsClickAllowed(true)
        }
        return current
    }

    fun searchVacancy(searchQuery: String) {
        if (searchQuery.isNotEmpty()) {
            Log.d("SearchQuery", "$searchQuery")
            if (!isNextPageLoading) {
                isNextPageLoading = true
                renderScreenState(SearchViewState.Loading)
                job = viewModelScope.launch {
                    try {
                        searchInteractor
                            .searchVacancy(searchQuery, 0)
                            .collect { viewState ->
                                renderScreenState(viewState)
                                isNextPageLoading = false
                            }

                    } finally {
                        isNextPageLoading = false
                    }
                }
            }
        }
    }

    fun onLastItemReached(query: String) {
        if (!(currentPage != maxPages && maxPages != 0) || !this.latestSearchQuery.equals(query)) {
            return
        } else if (query.isNotEmpty()) {
            if (!isNextPageLoading) {
                currentPage += 1
                job = viewModelScope.launch {
                    try {
                        isNextPageLoading = true
                        renderAdapterState(AdapterState.IsLoading)
                        searchInteractor
                            .searchVacancy(query, currentPage - 1)
                            .collect { viewState ->
                                renderScreenState(viewState)
                                isNextPageLoading = false
                            }
                    } finally {
                        isNextPageLoading = false
                    }
                    Log.d("CurrentPage", "$currentPage")
                    renderAdapterState(AdapterState.Idle)

                }
            }
        }
    }

    fun declineLastSearch() {
        job?.cancel()
        renderAdapterState(AdapterState.Idle)
        job = null
    }

    fun showVacancyDetails(vacancyItems: ListItem) {
        if (clickDebounce() && vacancyItems is ListItem.Vacancy) {
            _showVacancyDetails.value = vacancyItems.id
        }
    }

    fun resetVacancyDetails() {
        _showVacancyDetails.value = null
    }

    fun clearSearchList() {
        renderScreenState(
            SearchViewState.Base
        )
    }

    fun renderFilterState() {
        val filter = sharedPrefsInteractor.getFilter()
        Log.d("SearchFilterState", "$filter")
        if (checkFilterFieldsAreNull(filter) && filter.withSalary != true) {
            filterStateLiveData.postValue(false)
        } else {
            filterStateLiveData.postValue(true)
        }
    }

    private fun checkFilterFieldsAreNull(filter: Filter): Boolean {
        return with(filter) {
            areaCountry == null && areaCity == null && industrySP == null && salary == null
        }
    }

    private fun renderScreenState(state: SearchViewState) {
        if (state is SearchViewState.Success) {
            this.maxPages = state.vacancyList.pages
            Log.d("NewpageVacancies", "${state.vacancyList.items.size}")
            Log.d("VacanciesFound", "${state.vacancyList.items}")

            val updatedItems = state.vacancyList.items.map { vacancy -> mapper.map(vacancy) }

            stateLiveData.postValue(
                SearchViewState.Content(
                    updatedItems,
                    makeFoundVacanciesHint(state.vacancyList.found)
                )
            )
        } else {
            stateLiveData.postValue(state)
        }
    }

    private fun renderAdapterState(state: AdapterState) {
        adapterStateLiveData.value = state
    }

    private fun makeFoundVacanciesHint(vacanciesNumber: Int): String {
        return Converter.buildResultingSentence(vacanciesNumber, "Найдено")
    }

    override fun onCleared() {
        job = null
        super.onCleared()
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1_000L
        private const val SEARCH_DEBOUNCE_DELAY = 2_000L
    }

}
