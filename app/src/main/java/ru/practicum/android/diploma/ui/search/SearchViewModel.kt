package ru.practicum.android.diploma.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.usecase.SearchVacanciesUseCase
import ru.practicum.android.diploma.util.Resource

class SearchViewModel(
    private val searchVacanciesUseCase: SearchVacanciesUseCase
) : ViewModel() {

    companion object {
        private const val DEBOUNCE_PERIOD = 2000L
    }

    private val _searchState = MutableLiveData<SearchState>()
    val searchState: LiveData<SearchState> = _searchState

    private var currentQuery = ""
    private var currentPage = 0
    private var totalPages = 1
    private var isLoading = false
    private var isLoadingNextPage = false
    private var searchJob: Job? = null

    private val debouncePeriod = DEBOUNCE_PERIOD

    private val _allVacancies = mutableListOf<Vacancy>()
    val allVacancies: List<Vacancy> get() = _allVacancies

    fun search(query: String) {
        val trimmedQuery = query.trim()

        if (trimmedQuery.isEmpty()) {
            _allVacancies.clear()
            _searchState.value = SearchState.EmptyQuery
            return
        }

        if (trimmedQuery != currentQuery) {
            _allVacancies.clear()
            currentPage = 0
            totalPages = 1
            currentQuery = trimmedQuery
            _searchState.value = SearchState.Loading
            println("DEBUG: New search query: '$currentQuery', cleared vacancies")
        }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(debouncePeriod)
            performSearch(query = currentQuery, page = 0, isNewSearch = true)
        }
    }

    fun loadNextPage() {
        val canLoadNextPage = !isLoading &&
            !isLoadingNextPage &&
            hasMorePages() &&
            currentQuery.isNotEmpty()

        if (canLoadNextPage) {
            val nextPage = currentPage + 1
            println(
                "DEBUG: Loading next page: current=$currentPage, " +
                    "next=$nextPage, allVacancies size=${_allVacancies.size}"
            )
            performSearch(
                query = currentQuery,
                page = nextPage,
                isNewSearch = false
            )
        } else {
            println(
                "DEBUG: Cannot load next page - isLoading=$isLoading, " +
                    "isLoadingNextPage=$isLoadingNextPage, " +
                    "hasMorePages=${hasMorePages()}, " +
                    "queryEmpty=${currentQuery.isEmpty()}"
            )
        }
    }

    fun retry() {
        if (currentQuery.isNotEmpty()) {
            if (currentPage == 0) {
                performSearch(
                    query = currentQuery,
                    page = 0,
                    isNewSearch = true
                )
            } else {
                performSearch(
                    query = currentQuery,
                    page = currentPage,
                    isNewSearch = false
                )
            }
        }
    }

    fun clearSearch() {
        currentQuery = ""
        _allVacancies.clear()
        currentPage = 0
        totalPages = 1
        _searchState.value = SearchState.EmptyQuery
        println("DEBUG: Search cleared")
    }

    fun getCurrentQuery(): String {
        return currentQuery
    }

    fun isLoading(): Boolean {
        return isLoading || isLoadingNextPage
    }

    private fun performSearch(query: String, page: Int, isNewSearch: Boolean) {
        if (isLoading || isLoadingNextPage) {
            println(
                "DEBUG: Search already in progress - " +
                    "isLoading=$isLoading, isLoadingNextPage=$isLoadingNextPage"
            )
            return
        }

        setLoadingStates(isNewSearch)

        viewModelScope.launch {
            when (val result = searchVacanciesUseCase.execute(query, page)) {
                is Resource.Success -> handleSearchSuccess(result, isNewSearch)
                is Resource.Error -> handleSearchError(result, isNewSearch)
                is Resource.Loading -> handleSearchLoading()
            }
        }
    }

    private fun setLoadingStates(isNewSearch: Boolean) {
        if (isNewSearch) {
            isLoading = true
            _searchState.value = SearchState.Loading
            println("DEBUG: Starting new search - query='$currentQuery', page=$currentPage")
        } else {
            isLoadingNextPage = true
            _searchState.value = SearchState.LoadingNextPage
            println("DEBUG: Starting next page search - query='$currentQuery', page=$currentPage")
        }
    }

    private fun handleSearchSuccess(result: Resource.Success<*>, isNewSearch: Boolean) {
        val searchResult = result.data as ru.practicum.android.diploma.domain.models.SearchResult
        totalPages = searchResult.pages
        currentPage = searchResult.page

        println(
            "DEBUG: Search success - query='$currentQuery', " +
                "requestedPage=$currentPage, returnedPage=${searchResult.page}, " +
                "vacancies=${searchResult.vacancies.size}, totalPages=$totalPages"
        )
        println(
            "DEBUG: Before processing - allVacancies size: ${_allVacancies.size}, " +
                "new vacancies: ${searchResult.vacancies.size}"
        )

        if (isNewSearch) {
            handleNewSearchResults(searchResult)
        } else {
            handleNextPageResults(searchResult)
        }

        println(
            "DEBUG: Final state - currentPage=$currentPage, " +
                "totalPages=$totalPages, hasMorePages=${hasMorePages()}"
        )
    }

    private fun handleNewSearchResults(
        searchResult: ru.practicum.android.diploma.domain.models.SearchResult
    ) {
        _allVacancies.clear()
        _allVacancies.addAll(searchResult.vacancies)
        println("DEBUG: New search completed - allVacancies size: ${_allVacancies.size}")

        _searchState.value = SearchState.Success(
            vacancies = _allVacancies.toList(),
            found = searchResult.found,
            isFirstPage = true
        )
        isLoading = false
    }

    private fun handleNextPageResults(
        searchResult: ru.practicum.android.diploma.domain.models.SearchResult
    ) {
        val newVacancies = searchResult.vacancies
        println("DEBUG: Using all new vacancies: ${newVacancies.size} (duplicate filtering disabled)")

        _allVacancies.addAll(newVacancies)
        println("DEBUG: After adding - allVacancies size: ${_allVacancies.size}")

        _searchState.value = SearchState.Success(
            vacancies = _allVacancies.toList(),
            found = searchResult.found,
            isFirstPage = false
        )
        isLoadingNextPage = false
    }

    private fun handleSearchError(result: Resource.Error, isNewSearch: Boolean) {
        if (isNewSearch) {
            _searchState.value = SearchState.Error(result.message)
            isLoading = false
        } else {
            _searchState.value = SearchState.NextPageError(result.message)
            isLoadingNextPage = false
        }
        println("DEBUG: Search error - page=$currentPage, error=${result.message}")
    }

    private fun handleSearchLoading() {
        println("DEBUG: Search loading - page=$currentPage")
        /* handled by state */
    }

    fun hasMorePages(): Boolean {
        val hasMore = currentPage < totalPages - 1
        println(
            "DEBUG: hasMorePages check - currentPage=$currentPage, " +
                "totalPages=$totalPages, result=$hasMore"
        )
        return hasMore
    }

    // Метод для отладки текущего состояния
    fun debugState(): String {
        return "SearchViewModelState(query='$currentQuery', page=$currentPage, totalPages=$totalPages, " +
            "allVacancies=${_allVacancies.size}, isLoading=$isLoading, isLoadingNextPage=$isLoadingNextPage)"
    }
}
