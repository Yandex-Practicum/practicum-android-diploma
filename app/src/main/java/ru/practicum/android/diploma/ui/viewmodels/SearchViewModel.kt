package ru.practicum.android.diploma.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.models.ApiResult
import ru.practicum.android.diploma.domain.models.VacancyCard
import ru.practicum.android.diploma.util.CustomLiveData
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(
    private val repository: VacanciesRepository
) : ViewModel() {

    private var currentSearchPage: Int = 0
    private var maxPages: Int = Int.MAX_VALUE
    private val vacanciesList: MutableList<VacancyCard> = mutableListOf()
    private var isNextPageLoading = false
    private val _searchQuery = MutableStateFlow("")

    private var lastSearchRequest: String = ""
    private var searchJob: Job? = null

    private val _searchState: CustomLiveData<SearchState> = CustomLiveData()
    internal val searchState: LiveData<SearchState> get() = _searchState

    private val _searchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changeText ->
            searchVacancies(changeText)
        }

    init {
        viewModelScope.launch(Dispatchers.Main) {
            //
        }
    }

    fun searchDebounce(searchQuery: String) {
        if (searchQuery == lastSearchRequest) {
            return
        }
        if (searchQuery.isBlank()) {
            cancelSearch()
            _searchState.setValue(SearchState.QueryIsEmpty(isEmpty = true))
        } else {
            searchJob?.cancel()
            if (lastSearchRequest.isBlank()) {
                _searchState.setValue(SearchState.QueryIsEmpty(isEmpty = false))
            }
        }
        lastSearchRequest = searchQuery
        _searchDebounce(searchQuery)
        _searchState.setStartValue(SearchState.SearchText(searchQuery))
    }

    private fun searchVacancies(searchQuery: String) {
        if (searchQuery.isNotEmpty()) {
            renderLoadingState()
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                runCatching {
                    repository.searchVacancies(searchQuery, currentSearchPage)
                        // generateVacancySearchData()
                        .collect { result ->
                            withContext(Dispatchers.Main) {
                                val replaceVacancyList = currentSearchPage == 0
                                when (result) {
                                    is ApiResult.Loading -> {
                                        renderState(SearchState.IsLoading, replaceVacancyList)
                                    }
                                    is ApiResult.Error -> {
                                        when (result.httpCode) {
                                            -1 -> {
                                                if (currentSearchPage == 0) {
                                                    renderState(SearchState.ConnectionError(true), true)
                                                } else {
                                                    _searchState.setSingleEventValue(SearchState.ConnectionError(false))
                                                }
                                            }
                                            400, 404 -> {
                                                renderState(
                                                    SearchState.NotFoundError(replaceVacancyList),
                                                    replaceVacancyList
                                                )
                                            }
                                            500 -> {
                                                renderState(
                                                    SearchState.ServerError500(replaceVacancyList),
                                                    replaceVacancyList
                                                )
                                            }
                                        }
                                    }
                                    is ApiResult.Success -> {
                                        with(result.data) {
                                            isNextPageLoading = false
                                            maxPages = result.data.pagesCount
                                            if (result.data.vacanciesFound > 0) {
                                                vacanciesList.addAll(result.data.vacancies)
                                                renderState(SearchState.Content(vacanciesList, currentPage == 0), true)
                                                renderState(SearchState.VacanciesCount(result.data.vacanciesFound))
                                                ++currentSearchPage
                                            } else if (currentPage == 0) {
                                                renderState(SearchState.NotFoundError(true), true)
                                            } else {
                                                Unit
                                            }
                                        }
                                    }
                                }
                            }
                        }
                }.onFailure { er ->
                    Log.d("WEB", "Vacancy search error: $er")
                }
            }
        }
    }

    fun forceSearchLastRequest() {
        if (lastSearchRequest.isNotBlank()) {
            _searchState.clear()
            searchVacancies(lastSearchRequest)
        }
    }

    private fun renderState(newState: SearchState, clearOtherStates: Boolean = false) {
        if (clearOtherStates) {
            _searchState.clear()
            _searchState.setValue(
                SearchState.QueryIsEmpty(
                    isEmpty = lastSearchRequest.isBlank()
                )
            )
            _searchState.setStartValue(SearchState.SearchText(lastSearchRequest))
        }
        _searchState.setValue(newState)
    }

    private fun renderLoadingState() {
        if (currentSearchPage == 0) {
            renderState(SearchState.IsLoading, true)
        } else {
            _searchState.setSingleEventValue(SearchState.IsLoadingNextPage)
        }
    }

    fun cancelSearch() {
        searchJob?.cancel()
        _searchState.clear()
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
