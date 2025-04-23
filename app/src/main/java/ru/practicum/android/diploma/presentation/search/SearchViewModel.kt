package ru.practicum.android.diploma.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.dto.mapper.VacancyPageResult
import ru.practicum.android.diploma.data.utils.StringProvider
import ru.practicum.android.diploma.domain.interactor.SearchVacancyInteractor
import ru.practicum.android.diploma.domain.models.Resource

class SearchViewModel(
    private val searchVacancyInteractor: SearchVacancyInteractor,
    private val stringProvider: StringProvider
) : ViewModel() {
    private val _searchState = MutableStateFlow(SearchState())
    val searchState: StateFlow<SearchState> = _searchState
    private val _toastFlow = MutableSharedFlow<String>()
    val toastFlow = _toastFlow.asSharedFlow()

    fun searchVacancy(query: String) {
        viewModelScope.launch {
            _searchState.value = _searchState.value.copy(
                isLoading = true,
                query = query,
                isInitialLoading = true,
                isRefreshing = false
            )
            searchVacancyInteractor.searchVacancy(query, page = 0, perPage = PER_PAGE).collect { resource ->
                _searchState.value = when (resource) {
                    is Resource.Success -> buildSuccessState(resource.data, query)
                    is Resource.Empty -> buildEmptyState(query)
                    is Resource.Error -> buildErrorState(resource.message, query)
                }
            }
        }
    }

    fun loadNextPage() {
        val state = _searchState.value
        if (state.isLoading || state.isNextPageLoading) return
        if (state.currentPage + ROUND_UP_OFFSET >= state.totalPages) return

        viewModelScope.launch {
            _searchState.value = state.copy(isNextPageLoading = true)
            searchVacancyInteractor.searchVacancy(
                state.query,
                page = state.currentPage + ROUND_UP_OFFSET,
                perPage = PER_PAGE
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val newContent = state.content.orEmpty() + result.data.vacancies
                        _searchState.value = state.copy(
                            content = newContent.distinctBy { it.vacancyId },
                            currentPage = state.currentPage + ROUND_UP_OFFSET,
                            isNextPageLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _searchState.value = state.copy(isNextPageLoading = false)
                        _toastFlow.emit(stringProvider.getString(R.string.errors_check_connection))
                    }

                    else -> {
                        _searchState.value = state.copy(isNextPageLoading = false)
                    }
                }
            }
        }
    }

    fun refreshSearch() {
        val query = _searchState.value.query
        _searchState.value = _searchState.value.copy(isRefreshing = true, isLoading = false, isInitialLoading = false)

        viewModelScope.launch {
            searchVacancyInteractor.searchVacancy(query, page = 0, perPage = PER_PAGE).collect { resource ->
                _searchState.value = when (resource) {
                    is Resource.Success -> buildSuccessState(resource.data, query).copy(isRefreshing = false)
                    is Resource.Empty -> buildEmptyState(query).copy(isRefreshing = false)
                    is Resource.Error -> buildErrorState(resource.message, query).copy(isRefreshing = false)
                }
            }
        }
    }

    private fun buildSuccessState(
        result: VacancyPageResult,
        query: String
    ): SearchState {
        val count = result.found
        val plural = stringProvider.getQuantityString(R.plurals.vacancies_count, count, count)
        val text = stringProvider.getString(R.string.results_Find) + " " + plural

        return SearchState(
            isLoading = false,
            content = result.vacancies,
            currentPage = 0,
            totalPages = result.found / PER_PAGE + ROUND_UP_OFFSET,
            showResultText = true,
            resultText = text,
            query = query
        )
    }

    private fun buildEmptyState(query: String): SearchState {
        return SearchState(
            isLoading = false,
            error = UiError.BadRequest,
            resultText = stringProvider.getString(R.string.results_not_found),
            showResultText = true,
            query = query
        )
    }

    private fun buildErrorState(
        message: String,
        query: String
    ): SearchState {
        return SearchState(
            isLoading = false,
            error = mapError(message),
            query = query
        )
    }

    fun clearSearch() {
        _searchState.value = SearchState(
            content = emptyList(),
        )
    }

    private fun mapError(message: String?): UiError {
        return when {
            message?.contains(stringProvider.getString(R.string.errors_No_connection)) == true -> UiError.NoConnection
            message?.contains(stringProvider.getString(R.string.errors_bad_request)) == true -> UiError.BadRequest
            message?.contains(stringProvider.getString(R.string.errors_Server)) == true -> UiError.ServerError
            else -> UiError.ServerError
        }
    }

    companion object {
        private const val PER_PAGE = 20
        private const val ROUND_UP_OFFSET = 1
    }
}
