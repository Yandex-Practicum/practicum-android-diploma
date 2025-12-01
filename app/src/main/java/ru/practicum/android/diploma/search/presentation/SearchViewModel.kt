package ru.practicum.android.diploma.search.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.ApiError
import ru.practicum.android.diploma.data.ErrorCodes
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.models.VacanciesPage
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(
    private val searchInteractor: SearchInteractor,
) : ViewModel() {
    private var latestSearchText: String? = null
    private var lastState: SearchScreenState? = null
    private var _searchStatusLiveData = MutableLiveData<SearchScreenState>()
    val searchStatusLiveData: LiveData<SearchScreenState> = _searchStatusLiveData

    private val vacancySearchDebounce = debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText ->
        searchRequest(changedText)
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText != changedText) {
            latestSearchText = changedText
            vacancySearchDebounce(changedText)
        }
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isEmpty()) {
            renderState(SearchScreenState.Default)
            return
        }

        renderState(SearchScreenState.Loading)

        viewModelScope.launch {
            searchInteractor
                .getVacancies(newSearchText)
                .collect { result ->
                    result.fold(
                        onSuccess = { page -> handleSuccess(page) },
                        onFailure = { throwable -> handleError(throwable) }
                    )
                }
        }
    }

    private fun handleSuccess(page: VacanciesPage) {
        val list = page.vacancies

        when {
            list.isEmpty() -> {
                renderState(
                    SearchScreenState.Error(
                        UiError.NothingFound
                    )
                )
            }
            else -> {
                renderState(
                    SearchScreenState.ShowContent(
                        vacancies = ArrayList(list),
                        found = page.found
                    )
                )
            }
        }
    }

    private fun handleError(throwable: Throwable) {
        Log.d(TAG, "$throwable")

        val uiError: UiError = when (throwable) {
            is java.net.UnknownHostException -> UiError.NoInternet
            is ApiError -> when (throwable.code) {
                ErrorCodes.ERROR_NO_INTERNET -> UiError.NoInternet
                ErrorCodes.NOTHING_FOUND -> UiError.NothingFound
                ErrorCodes.IO_EXCEPTION,
                ErrorCodes.MAPPER_EXCEPTION -> UiError.ServerError
                else -> UiError.Unknown(throwable.code)
            }
            else -> UiError.Unknown(-999)
        }

        renderState(SearchScreenState.Error(uiError))
    }

    private fun renderState(state: SearchScreenState) {
        lastState = state
        _searchStatusLiveData.postValue(state)
    }

    companion object {
        private const val TAG = "SearchViewModel"
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
