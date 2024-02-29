package ru.practicum.android.diploma.search.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.model.SearchFilterParameters
import ru.practicum.android.diploma.favourites.presentation.CLICK_DEBOUNCE_DELAY
import ru.practicum.android.diploma.search.domain.usecase.SearchVacancyUseCase
import ru.practicum.android.diploma.util.Resource

class SearchViewModel(private val searchVacancyUseCase: SearchVacancyUseCase) : ViewModel() {
    private var isClickAllowed = true
    private val stateLiveData = MutableLiveData<SearchState>(SearchState.Default)
    private var isSearchByPageAllowed = true
    private var searchByTextJob: Job? = null
    private var previousSearchText = ""

    fun observeState(): LiveData<SearchState> = stateLiveData

    private fun renderState(state: SearchState) {
        stateLiveData.postValue(state)
    }

    fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewModelScope.launch(Dispatchers.IO) {
                delay(CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
        }
        return current
    }

    fun searchByText(searchText: String, filterParameters: SearchFilterParameters) {
        searchByTextJob?.cancel()
        if (searchText.isNotEmpty() && searchText != previousSearchText) {
            searchByTextJob = viewModelScope.launch(Dispatchers.IO) {
                delay(SEARCH_DEBOUNCE_DELAY)
                previousSearchText = searchText
                renderState(SearchState.Loading)
                searchVacancyUseCase.execute(searchText, 0, filterParameters).collect {
                    when (it) {
                        is Resource.Success -> {
                            if (it.data?.vacancies.isNullOrEmpty()) {
                                stateLiveData.postValue(SearchState.EmptyResult)
                            } else {
                                clearSearch()
                                Log.v("OkHttp", "searchText = $searchText")
                                stateLiveData.postValue(SearchState.Content(it.data))
                            }
                        }

                        is Resource.InternetError -> {
                            stateLiveData.postValue(SearchState.NetworkError)
                        }

                        is Resource.ServerError -> {
                            stateLiveData.postValue(SearchState.EmptyResult)
                        }
                    }
                }
            }
        } else if (searchText.isEmpty()) {
            clearSearch()
        }
    }

    fun searchByPage(searchText: String, page: Int, filterParameters: SearchFilterParameters) {
        if (isSearchByPageAllowed && searchText.isNotEmpty() && searchByTextJob?.isCompleted != false) {
            isSearchByPageAllowed = false
            viewModelScope.launch(Dispatchers.IO) {
                searchVacancyUseCase.execute(searchText, page, filterParameters).collect {
                    if (it is Resource.Success && !it.data?.vacancies.isNullOrEmpty()) {
                        stateLiveData.postValue(SearchState.Content(it.data))
                    }
                    delay(SEARCH_PAGINATION_DELAY)
                    isSearchByPageAllowed = true
                }
            }
        }
    }

    fun clearSearch() {
        stateLiveData.postValue(SearchState.Default)
    }

    companion object {
        private const val SEARCH_PAGINATION_DELAY = 500L
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
