package ru.practicum.android.diploma.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.network.NetworkCodes
import ru.practicum.android.diploma.domain.api.SearchVacanciesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancySearchFilter
import ru.practicum.android.diploma.domain.models.VacancySearchResult
import ru.practicum.android.diploma.domain.util.ErrorType
import ru.practicum.android.diploma.util.Event

class SearchViewModel(private val searchVacanciesInteractor: SearchVacanciesInteractor) : ViewModel() {
    private val _searchStateLiveData = MutableLiveData<SearchState>()
    val searchStateLiveData: LiveData<SearchState> = _searchStateLiveData
    private val _uiEvent = MutableLiveData<Event<UIEvent>>()
    val uiEvent: LiveData<Event<UIEvent>> = _uiEvent
    private var latestSearchText: String? = null
    private var searchJob: Job? = null
    private var debounceJob: Job? = null
    private var currentPage = PAGES_START
    private var totalPages = Int.MAX_VALUE
    private var isNextPageLoading = false
    private var isPagingError = false
    private var hasShownPagingErrorToast = false
    private val loadedVacancies = mutableListOf<Vacancy>()
    private val loadedPages = mutableMapOf<String, MutableSet<Int>>()
    private var totalFoundFromApi = 0

    fun searchDebounce(changedText: String) {
        debounceJob?.cancel()
        if (changedText.isEmpty()) {
            handleEmptyQuery()
            return
        }

        if (latestSearchText == changedText) {
            return
        }

        debounceJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            search(changedText)
        }
    }

    fun searchIme(query: String) {
        if (query.isEmpty()) {
            handleEmptyQuery()
            return
        }
        search(query)
    }

    private fun search(query: String) {
        if (query.isEmpty()) {
            handleEmptyQuery()
            return
        }
        val isQueryChanged = latestSearchText != query
        latestSearchText = query

        if (isQueryChanged) {
            resetPagination()
            loadedPages.clear()
            _searchStateLiveData.value = SearchState.Initial
        }

        if (currentPage == PAGES_START) {
            _searchStateLiveData.value = SearchState.Loading
        }

        val pagesForQuery = loadedPages.getOrPut(query) { mutableSetOf() }

        if (shouldSkipPageLoad(pagesForQuery)) {
            if (loadedVacancies.isNotEmpty()) {
                _searchStateLiveData.value = SearchState.Content(
                    vacancies = loadedVacancies.toList(),
                    totalFound = totalFoundFromApi
                )
            }
            return
        }

        if (searchStateLiveData.value is SearchState.Error) {
            resetPagination()
        }
        loadPage(query, pagesForQuery)
    }

    private fun handleEmptyQuery() {
        resetPagination()
        latestSearchText = ""
        loadedVacancies.clear()
        _searchStateLiveData.value = SearchState.Initial
    }

    private fun shouldSkipPageLoad(pagesForQuery: Set<Int>): Boolean {
        return isNextPageLoading || currentPage > totalPages || pagesForQuery.contains(currentPage)
    }

    private fun loadPage(query: String, pagesForQuery: MutableSet<Int>) {
        isNextPageLoading = true
        searchJob?.cancel()
        debounceJob?.cancel()
        searchJob = viewModelScope.launch {
            setLoadingState(latestSearchText == query && currentPage == PAGES_START)

            searchVacanciesInteractor
                .searchVacancies(VacancySearchFilter(text = query, page = currentPage))
                .collect { result ->
                    handleSearchResult(result, pagesForQuery)
                    isNextPageLoading = false
                }
        }
    }

    private fun setLoadingState(isQueryChanged: Boolean) {
        if (currentPage == PAGES_START && isQueryChanged) {
            _searchStateLiveData.value = SearchState.Loading
        } else if (currentPage > PAGES_START) {
            _searchStateLiveData.value = SearchState.LoadingPage
        }
    }

    private fun handleSearchResult(
        result: VacancySearchResult,
        pagesForQuery: MutableSet<Int>
    ) {
        if (result.errorCode == NetworkCodes.SUCCESS_CODE) {
            handleSuccess(result, pagesForQuery)
        } else {
            handleError(result)
        }
    }

    private fun handleSuccess(result: VacancySearchResult, pagesForQuery: MutableSet<Int>) {
        isPagingError = false
        hasShownPagingErrorToast = false
        totalPages = result.totalPages
        totalFoundFromApi = result.totalFound
        loadedVacancies.addAll(result.vacancies)
        _searchStateLiveData.value = if (loadedVacancies.isEmpty()) {
            SearchState.Empty
        } else {
            SearchState.Content(
                vacancies = loadedVacancies.toList(),
                totalFound = totalFoundFromApi
            )
        }
        if (result.vacancies.isNotEmpty()) {
            pagesForQuery.add(currentPage)
        }
    }

    private fun handleError(result: VacancySearchResult) {
        isPagingError = true
        if (!hasShownPagingErrorToast) {
            _uiEvent.value = Event(
                when (ErrorType.fromCode(result.errorCode)) {
                    ErrorType.NO_INTERNET -> UIEvent.ShowNoInternetToast
                    else -> UIEvent.ShowGenericErrorToast
                }
            )
            hasShownPagingErrorToast = true
        }
        if (loadedVacancies.isEmpty()) {
            _searchStateLiveData.value = SearchState.Error(ErrorType.fromCode(result.errorCode))
        } else {
            _searchStateLiveData.value = SearchState.Content(
                vacancies = loadedVacancies.toList(),
                totalFound = totalFoundFromApi
            )
        }
    }

    fun isFirstPage(): Boolean = currentPage == PAGES_START

    fun onLastItemReached() {
        if (latestSearchText.isNullOrEmpty() || isNextPageLoading || currentPage == totalPages) {
            return
        }
        if (isPagingError) {
            isPagingError = false
            search(latestSearchText!!)
        } else {
            if (currentPage < totalPages) {
                currentPage++
                search(latestSearchText!!)
            }
        }
    }

    private fun resetPagination() {
        currentPage = PAGES_START
        totalPages = Int.MAX_VALUE
        isNextPageLoading = false
        isPagingError = false
        hasShownPagingErrorToast = false
        loadedVacancies.clear()
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val PAGES_START = 1
    }
}
