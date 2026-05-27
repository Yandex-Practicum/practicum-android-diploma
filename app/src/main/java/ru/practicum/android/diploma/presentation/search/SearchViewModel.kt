package ru.practicum.android.diploma.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactors.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.VacancySearchRequest
import ru.practicum.android.diploma.domain.models.VacancySearchResult
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(
    private val vacanciesInteractor: VacanciesInteractor,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _paginationError = MutableSharedFlow<SearchError>()
    val paginationError: SharedFlow<SearchError> = _paginationError.asSharedFlow()

    private val loadedPages = mutableSetOf<Int>()
    private var lastSearchQuery: String = ""
    private var paginationJob: Job? = null

    private val searchDebounce = debounce<String>(
        delayMillis = SEARCH_DEBOUNCE_MILLIS,
        coroutineScope = viewModelScope,
        useLastParam = true,
    ) { query ->
        searchPage(query, page = VacancySearchRequest.FIRST_PAGE, isPagination = false)
    }

    fun onQueryChanged(query: String) {
        paginationJob?.cancel()
        paginationJob = null
        loadedPages.clear()
        _uiState.update { state ->
            state.copy(
                query = query,
                isLoading = false,
                found = 0,
                vacancies = emptyList(),
                errorType = null,
                currentPage = VacancySearchRequest.FIRST_PAGE,
                totalPages = 0,
                isNextPageLoading = false,
            )
        }

        if (query.isBlank()) {
            searchDebounce.cancel()
            lastSearchQuery = ""
            return
        }

        searchDebounce(query)
    }

    fun onClearQueryClicked() {
        onQueryChanged(query = "")
    }

    fun loadNextPage() {
        val state = _uiState.value
        val nextPage = state.currentPage + 1
        val canLoad = !state.isNextPageLoading
            && nextPage !in loadedPages
            && (state.totalPages == 0 || nextPage <= state.totalPages)
        if (!canLoad) return
        paginationJob = viewModelScope.launch {
            searchPage(state.query, page = nextPage, isPagination = true)
        }
    }

    private suspend fun searchPage(query: String, page: Int, isPagination: Boolean) {
        setLoadingState(isPagination)
        val request = VacancySearchRequest(text = query, page = page)
        when (val result = vacanciesInteractor.searchVacancies(request)) {
            is Resource.Success -> handleSuccess(result, page, isPagination)
            is Resource.Error -> handleError(result, isPagination)
            Resource.Loading -> Unit
        }
    }

    private fun setLoadingState(isPagination: Boolean) {
        if (isPagination) {
            _uiState.update { it.copy(isNextPageLoading = true) }
        } else {
            _uiState.update { it.copy(isLoading = true, errorType = null) }
        }
    }

    private fun handleSuccess(
        result: Resource.Success<VacancySearchResult>,
        page: Int,
        isPagination: Boolean,
    ) {
        loadedPages.add(page)
        val data = result.data
        val isEmpty = !isPagination && data.vacancies.isEmpty()
        _uiState.update { state ->
            state.copy(
                isLoading = false,
                isNextPageLoading = false,
                found = data.found,
                currentPage = data.page,
                totalPages = data.pages,
                vacancies = if (isPagination) {
                    (state.vacancies + data.vacancies).distinctBy { it.id }
                } else {
                    data.vacancies
                },
                errorType = if (isEmpty) SearchError.EMPTY else null,
            )
        }
    }

    private suspend fun handleError(result: Resource.Error, isPagination: Boolean) {
        val errorType = if (result.code == null) {
            SearchError.NO_INTERNET
        } else {
            SearchError.SERVER_ERROR
        }
        if (isPagination) {
            _uiState.update { it.copy(isNextPageLoading = false) }
            _paginationError.emit(errorType)
        } else {
            _uiState.update { it.copy(isLoading = false, errorType = errorType) }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_MILLIS = 2_000L
    }
}
