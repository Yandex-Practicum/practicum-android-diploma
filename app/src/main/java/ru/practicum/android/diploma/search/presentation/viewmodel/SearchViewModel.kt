package ru.practicum.android.diploma.search.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.interactor.SearchInteractor
import ru.practicum.android.diploma.search.domain.model.SearchViewState

class SearchViewModel(
    private val searchInteractor: SearchInteractor
) : ViewModel() {

    private var currentPage: Int = 0

    private val stateLiveData = MutableLiveData<SearchViewState>()
    fun observeState(): LiveData<SearchViewState> = stateLiveData

    fun searchVacancy(searchQuery: String) {
        if (searchQuery.isNotEmpty()) {
            renderState(SearchViewState.Loading)
            viewModelScope.launch {
                searchInteractor
                    .searchVacancy(searchQuery, currentPage)
                    .collect { viewState ->
                        renderState(viewState) }
            }
        }
    }

    private fun renderState(state: SearchViewState) {
        stateLiveData.postValue(state)
    }
}
