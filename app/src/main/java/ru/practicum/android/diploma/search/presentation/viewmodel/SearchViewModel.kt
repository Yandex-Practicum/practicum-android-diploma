package ru.practicum.android.diploma.search.presentation.viewmodel

import android.util.Log
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
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

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
        if (!(currentPage != maxPages && maxPages != 0)) return
        if (this.latestSearchQuery != query) return
        if (query.isEmpty()) return

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
                renderAdapterState(AdapterState.Idle)
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
            Log.d("NewpageVacancies", "${state.vacancyList.items.size}")
            stateLiveData.postValue(
                SearchViewState.Content(
                    state.vacancyList.items.map { vacancy -> Mapper.map(vacancy) },
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
        return when {
            vacanciesNumber % 10 == 0 -> "$FOUND_LITERAL ${formatFoundVacanciesNumber(vacanciesNumber)} вакансий"
            vacanciesNumber % 10 == 1 -> "Найдена ${formatFoundVacanciesNumber(vacanciesNumber)} вакансия"
            vacanciesNumber % 10 in 2..4 -> "$FOUND_LITERAL ${formatFoundVacanciesNumber(vacanciesNumber)} вакансии"
            vacanciesNumber % 10 in 5..9 -> "$FOUND_LITERAL ${formatFoundVacanciesNumber(vacanciesNumber)} вакансий"
            vacanciesNumber % 100 in 11..19 -> "$FOUND_LITERAL ${formatFoundVacanciesNumber(vacanciesNumber)} вакансий"
            else -> ""
        }
    }

    private fun formatFoundVacanciesNumber(vacanciesNumber: Int?): String {
        if (vacanciesNumber == null) return ""

        val dfs = DecimalFormatSymbols().apply {
            groupingSeparator = ' '
        }
        val df = DecimalFormat("###,###", dfs)
        return df.format(vacanciesNumber)
    }

    companion object {
        private const val FOUND_LITERAL = "Найдено"
        private const val CLICK_DEBOUNCE_DELAY = 1_000L
        private const val SEARCH_DEBOUNCE_DELAY = 2_000L
    }

}
