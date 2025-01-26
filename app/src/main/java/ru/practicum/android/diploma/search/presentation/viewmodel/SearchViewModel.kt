package ru.practicum.android.diploma.search.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.data.Mapper
import ru.practicum.android.diploma.common.util.Converter
import ru.practicum.android.diploma.common.util.debounce
import ru.practicum.android.diploma.search.domain.interactor.SearchInteractor
import ru.practicum.android.diploma.search.domain.model.AdapterState
import ru.practicum.android.diploma.search.domain.model.SearchViewState
import ru.practicum.android.diploma.search.presentation.items.ListItem

class SearchViewModel(
    private val searchInteractor: SearchInteractor,
    private val mapper: Mapper,
) : ViewModel() {

    private var currentPage: Int = 1
    private var maxPages: Int? = 0
    private var latestSearchQuery: String? = null
    private var vacancyList = mutableListOf<ListItem>()
    private val emptyVacancyList = emptyList<ListItem>()
    private var isNextPageLoading: Boolean = false
    private var isClickAllowed = true

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

    private val showVacancyDetails = MutableLiveData<String>()
    fun getShowVacancyDetails(): LiveData<String> = showVacancyDetails

    private val stateLiveData = MutableLiveData<SearchViewState>()
    fun observeState(): LiveData<SearchViewState> = stateLiveData

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
            Log.d("SearchQuery","$searchQuery")
            if (!isNextPageLoading) {
                isNextPageLoading = true
                renderScreenState(SearchViewState.Loading)
                viewModelScope.launch {
                    searchInteractor
                        .searchVacancy(searchQuery, 1)
                        .collect { viewState ->
                            renderScreenState(viewState)
                        }
                    isNextPageLoading = false
                }
            }
        }
    }

    fun onLastItemReached(query: String) {
        if (!(currentPage != maxPages && maxPages != 0) || this.latestSearchQuery != query) return
        if (query.isNotEmpty()) {
            if (!isNextPageLoading) {
                currentPage += 1
                viewModelScope.launch {
                    isNextPageLoading = true
                    renderAdapterState(AdapterState.IsLoading)
                    searchInteractor
                        .searchVacancy(query, currentPage)
                        .collect { viewState ->
                            renderScreenState(viewState)
                        }
                    Log.d("CurrentPage","$currentPage")
                    renderAdapterState(AdapterState.Idle)
                    isNextPageLoading = false
                }
            }
        }
    }

    fun showVacancyDetails(vacancyItems: ListItem) {
        if (clickDebounce()) {
            when {
                vacancyItems is ListItem.Vacancy -> {
                    showVacancyDetails.postValue(vacancyItems.id)
                }

                else -> return
            }
        }
    }

    fun clearSearchList() {
        renderScreenState(
            SearchViewState.Content(
                emptyVacancyList,
                null
            )
        )
    }

    private fun renderScreenState(state: SearchViewState) {
        if (state is SearchViewState.Success) {
            this.maxPages = state.vacancyList.pages
            Log.d("NewpageVacancies", "${state.vacancyList.items.size}")
            Log.d("VacanciesFound", "${state.vacancyList.items}")
            stateLiveData.postValue(
                SearchViewState.Content(
                    state.vacancyList.items.map { vacancy -> mapper.map(vacancy) },
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

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1_000L
        private const val SEARCH_DEBOUNCE_DELAY = 2_000L
    }

}
