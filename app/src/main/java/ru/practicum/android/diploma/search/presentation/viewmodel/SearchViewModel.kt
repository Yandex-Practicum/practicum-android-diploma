package ru.practicum.android.diploma.search.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.util.debounce
import ru.practicum.android.diploma.search.domain.interactor.SearchInteractor
import ru.practicum.android.diploma.search.domain.model.AdapterState
import ru.practicum.android.diploma.search.domain.model.SearchViewState
import ru.practicum.android.diploma.search.presentation.list_items.ListItem
import ru.practicum.android.diploma.search.presentation.list_items.Mapper

class SearchViewModel(
    private val searchInteractor: SearchInteractor,
) : ViewModel() {

    private var currentPage: Int = 0
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
    )
    { query ->
        searchVacancy(query)
    }

    private val setIsClickAllowed = debounce<Boolean>(
        CLICK_DEBOUNCE_DELAY,
        viewModelScope,
        useLastParam = false
    )
    { isAllowed ->
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
            if (!isNextPageLoading) {
                isNextPageLoading = true
                renderScreenState(SearchViewState.Loading)
                viewModelScope.launch {
                    searchInteractor
                        .searchVacancy(searchQuery, 1)
                        .collect { viewState ->
                            renderScreenState(viewState)
                        }
                }
            }

        }
        isNextPageLoading = false
    }

    fun onLastItemReached(query: String) {
        if (currentPage != maxPages && maxPages != 0) {
            if (this.latestSearchQuery != query) {
                return
            } else {
                currentPage += 1
                if (query.isNotEmpty()) {
                    if (!isNextPageLoading) {
                        isNextPageLoading = true
                        renderAdapterState(
                            AdapterState.IsLoading
                        )
                        viewModelScope.launch {
                            searchInteractor
                                .searchVacancy(query, currentPage)
                                .collect { viewState ->
                                    renderScreenState(viewState)
                                }
                        }
                        renderAdapterState(
                            AdapterState.Idle
                        )
                    }
                }
                isNextPageLoading = false
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
            this.vacancyList += state.vacancyList.items.map { vacancy -> Mapper.map(vacancy) }
            stateLiveData.postValue(
                SearchViewState.Content(
                    this.vacancyList,
                    makeFoundVacanciesHint(state.vacancyList.found)
                )
            )
//        if (state is SearchViewState.Success) {
//            this.maxPages = state.vacancyList.pages
//            this.vacancyList += state.vacancyList.items
//            val vacancyList = state.vacancyList.copy(items = this.vacancyList)
//            stateLiveData.postValue(
//                SearchViewState.Success(vacancyList)
//            )
        } else {
            stateLiveData.postValue(state)
        }
    }

    private fun renderAdapterState(state: AdapterState){
        adapterStateLiveData.postValue(state)
    }

    private fun makeFoundVacanciesHint(vacanciesNumber: Int): String {
        return when {
            vacanciesNumber % 10 == 0 -> "Найдено $vacanciesNumber вакансий"
            vacanciesNumber % 10 == 1 -> "Найдена $vacanciesNumber вакансия"
            vacanciesNumber % 10 in 2..4 -> "Найдено $vacanciesNumber вакансии"
            vacanciesNumber % 10 in 5..9 -> "Найдено $vacanciesNumber вакансий"
            vacanciesNumber % 100 in 11..19 -> "Найдено $vacanciesNumber вакансий"
            else -> ""
        }
    }

    companion object {
        private val SEARCH_REQUEST_TOKEN = Any()

        private const val KEY = "KEY"
        private const val TRACK_ITEM_KEY = "trackItem"
        private const val CLICK_DEBOUNCE_DELAY = 1_000L
        private const val SEARCH_DEBOUNCE_DELAY = 2_000L
    }


}
