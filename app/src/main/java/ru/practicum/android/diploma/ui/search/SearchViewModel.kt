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

class SearchViewModel(private val searchVacanciesInteractor: SearchVacanciesInteractor) : ViewModel() {
    private val _searchStateLiveData = MutableLiveData<SearchState>()
    val searchStateLiveData: LiveData<SearchState> = _searchStateLiveData
    private var latestSearchText: String? = null
    private var searchJob: Job? = null
    private var debounceJob: Job? = null

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText &&
            (searchStateLiveData.value is SearchState.Content ||
                searchStateLiveData.value is SearchState.Empty)
        ) {
            return
        }

        this.latestSearchText = changedText
        debounceJob?.cancel()
        if (changedText.isEmpty()) {
            searchJob?.cancel()
        } else {
            debounceJob = viewModelScope.launch {
                delay(SEARCH_DEBOUNCE_DELAY)
                search(changedText)
            }
        }
    }

    fun search(query: String) {
        if (query.isEmpty()) return
        latestSearchText = query
        searchJob?.cancel()
        debounceJob?.cancel()
        searchJob = viewModelScope.launch {
            _searchStateLiveData.value = SearchState.Loading
            searchVacanciesInteractor.searchVacancies(query).collect { resource ->
                _searchStateLiveData.value = when {
                    resource.errorCode != NetworkCodes.SUCCESS_CODE ->
                        SearchState.Error(resource.errorCode)
                    resource.vacancies.isEmpty() -> SearchState.Empty
                    else -> SearchState.Content(
                        vacancies = resource.vacancies,
                        totalFound = resource.totalFound
                    )
                }
            }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
