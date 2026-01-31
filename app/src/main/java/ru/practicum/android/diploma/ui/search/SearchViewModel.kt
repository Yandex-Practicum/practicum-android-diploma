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
import ru.practicum.android.diploma.domain.util.ErrorType

class SearchViewModel(private val searchVacanciesInteractor: SearchVacanciesInteractor) : ViewModel() {
    private val _searchStateLiveData = MutableLiveData<SearchState>()
    val searchStateLiveData: LiveData<SearchState> = _searchStateLiveData
    private var latestSearchText: String? = null
    private var searchJob: Job? = null
    private var debounceJob: Job? = null
    private var currentPage = PAGES_START
    private var totalPages = Int.MAX_VALUE
    private var isNextPageLoading = false
    private val loadedVacancies = mutableListOf<Vacancy>()
    private val loadedPages = mutableMapOf<String, MutableSet<Int>>()

    fun searchDebounce(changedText: String) {
        if (changedText.isEmpty()) {
            resetPagination()
            _searchStateLiveData.value = SearchState.Initial
            latestSearchText = changedText
            searchJob?.cancel()
            debounceJob?.cancel()
            return
        }

        if (latestSearchText == changedText &&
            (searchStateLiveData.value is SearchState.Content ||
                searchStateLiveData.value is SearchState.Empty)
        ) {
            return
        }

        debounceJob?.cancel()
        debounceJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            search(changedText)
        }
    }

    fun searchIme(query: String) {
        search(query)
    }

    fun search(query: String) {
        val isQueryChanged = latestSearchText != query
        latestSearchText = query

        if (query.isEmpty()) {
            resetPagination()
            _searchStateLiveData.value = SearchState.Initial
            return
        }

        if (isQueryChanged) {
            resetPagination()
            _searchStateLiveData.value = SearchState.Loading
        }
        val pagesForQuery = loadedPages.getOrPut(query) { mutableSetOf() }

        if (isNextPageLoading || currentPage > totalPages) return

        if (pagesForQuery.contains(currentPage)) {
            _searchStateLiveData.value =
                if (loadedVacancies.isEmpty()) SearchState.Empty
                else SearchState.Content(
                    vacancies = loadedVacancies.toList(),
                    totalFound = loadedVacancies.size
                )
            return
        }

        isNextPageLoading = true
        searchJob?.cancel()
        debounceJob?.cancel()
        searchJob = viewModelScope.launch {
            if (currentPage == PAGES_START && isQueryChanged) {
                _searchStateLiveData.value = SearchState.Loading
            } else if (currentPage > PAGES_START) {
                _searchStateLiveData.value = SearchState.LoadingPage
            }
            searchVacanciesInteractor
                .searchVacancies(
                    VacancySearchFilter(
                        text = query,
                        page = currentPage
                    )
                )
                .collect { result ->

                    isNextPageLoading = false

                    if (result.errorCode != NetworkCodes.SUCCESS_CODE) {
                        _searchStateLiveData.value =
                            SearchState.Error(ErrorType.fromCode(result.errorCode))
                        return@collect
                    }

                    totalPages = result.totalPages
                    loadedVacancies.addAll(result.vacancies)

                    _searchStateLiveData.value =
                        if (loadedVacancies.isEmpty()) {
                            SearchState.Empty
                        } else {
                            SearchState.Content(
                                vacancies = loadedVacancies.toList(),
                                totalFound = result.totalFound
                            )
                        }
                }
        }
    }

    fun isFirstPage(): Boolean = currentPage == PAGES_START

    fun onLastItemReached() {
        if (latestSearchText.isNullOrEmpty()) return
        if (isNextPageLoading) return
        if (currentPage >= totalPages) return

        currentPage++
        search(latestSearchText!!)
    }

    private fun resetPagination() {
        currentPage = PAGES_START
        totalPages = Int.MAX_VALUE
        isNextPageLoading = false
        loadedVacancies.clear()
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val PAGES_START = 1
    }
}
