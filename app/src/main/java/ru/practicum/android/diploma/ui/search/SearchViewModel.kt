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

    private val _searchState = MutableLiveData<SearchState>()
    val searchState: LiveData<SearchState> = _searchState

    private var currentQuery = ""
    private var currentPage = 0
    private var totalPages = 1
    private var isLoading = false
    private var isLoadingNextPage = false
    private var searchJob: Job? = null

    private val debouncePeriod = 2000L

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
        if (!isLoading && !isLoadingNextPage && hasMorePages() && currentQuery.isNotEmpty()) {
            // ВАЖНО: загружаем следующую страницу (currentPage + 1)
            val nextPage = currentPage + 1
            println("DEBUG: Loading next page: current=$currentPage, next=$nextPage, allVacancies size=${_allVacancies.size}")
            performSearch(query = currentQuery, page = nextPage, isNewSearch = false)
        } else {
            println("DEBUG: Cannot load next page - isLoading=$isLoading, isLoadingNextPage=$isLoadingNextPage, hasMorePages=${hasMorePages()}, queryEmpty=${currentQuery.isEmpty()}")
        }
    }

    fun retry() {
        if (currentQuery.isNotEmpty()) {
            if (currentPage == 0) {
                performSearch(query = currentQuery, page = 0, isNewSearch = true)
            } else {
                performSearch(query = currentQuery, page = currentPage, isNewSearch = false)
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
            println("DEBUG: Search already in progress - isLoading=$isLoading, isLoadingNextPage=$isLoadingNextPage")
            return
        }

        if (isNewSearch) {
            isLoading = true
            _searchState.value = SearchState.Loading
            println("DEBUG: Starting new search - query='$query', page=$page")
        } else {
            isLoadingNextPage = true
            _searchState.value = SearchState.LoadingNextPage
            println("DEBUG: Starting next page search - query='$query', page=$page")
        }

        viewModelScope.launch {
            when (val result = searchVacanciesUseCase(query, page)) {
                is Resource.Success -> {
                    val searchResult = result.data
                    totalPages = searchResult.pages
                    currentPage = searchResult.page

                    println("DEBUG: Search success - query='$query', requestedPage=$page, returnedPage=${searchResult.page}, vacancies=${searchResult.vacancies.size}, totalPages=$totalPages")
                    println("DEBUG: Before processing - allVacancies size: ${_allVacancies.size}, new vacancies: ${searchResult.vacancies.size}")

                    if (isNewSearch) {
                        _allVacancies.clear()
                        _allVacancies.addAll(searchResult.vacancies)
                        println("DEBUG: New search completed - allVacancies size: ${_allVacancies.size}")

                        _searchState.value = SearchState.Success(
                            vacancies = _allVacancies.toList(),
                            found = searchResult.found,
                            isFirstPage = true
                        )
                        isLoading = false
                    } else {
                        // ВРЕМЕННО ОТКЛЮЧАЕМ ФИЛЬТРАЦИЮ ДУБЛИКАТОВ
                        val newVacancies = searchResult.vacancies // Просто берем все вакансии

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

                    println("DEBUG: Final state - currentPage=$currentPage, totalPages=$totalPages, hasMorePages=${hasMorePages()}")
                }
                is Resource.Error -> {
                    if (isNewSearch) {
                        _searchState.value = SearchState.Error(result.message)
                        isLoading = false
                    } else {
                        _searchState.value = SearchState.NextPageError(result.message)
                        isLoadingNextPage = false
                    }
                    println("DEBUG: Search error - page=$page, error=${result.message}")
                }
                is Resource.Loading -> {
                    println("DEBUG: Search loading - page=$page")
                    /* handled by state */
                }
            }
        }
    }

    fun hasMorePages(): Boolean {
        val hasMore = currentPage < totalPages - 1
        println("DEBUG: hasMorePages check - currentPage=$currentPage, totalPages=$totalPages, result=$hasMore")
        return hasMore
    }

    // Метод для отладки текущего состояния
    fun debugState(): String {
        return "SearchViewModelState(query='$currentQuery', page=$currentPage, totalPages=$totalPages, " +
            "allVacancies=${_allVacancies.size}, isLoading=$isLoading, isLoadingNextPage=$isLoadingNextPage)"
    }
}
