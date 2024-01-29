package ru.practicum.android.diploma.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.SearchInteractor
import ru.practicum.android.diploma.domain.models.ErrorNetwork
import ru.practicum.android.diploma.domain.models.Vacancy
import kotlin.reflect.KProperty

class SearchViewModel(
    private val interactor: SearchInteractor
) : ViewModel() {

    private var latestSearchText: String? = null
    private var searchJob: Job? = null
    private val stateLiveData = MutableLiveData<SearchState>()
    fun observeState(): LiveData<SearchState> = stateLiveData
    private fun renderState(state: SearchState) {
        stateLiveData.postValue(state)
    }

    private val clearTextState = MutableLiveData<ClearTextState>(ClearTextState.None)
    fun observeClearTextState(): LiveData<ClearTextState> = clearTextState
    fun textCleared() {
        clearTextState.value = ClearTextState.None
        searchJob?.cancel()
    }
    fun onTextChanged(searchText: String?) {
        if (searchText.isNullOrEmpty()) {
            renderState(SearchState.EmptyScreen)
        } else {
            searchDebounce(searchText)
        }
    }
    fun onClearTextPressed() {
        clearTextState.value = ClearTextState.ClearText

    }

    public override fun onCleared() {
        super.onCleared()
    }


    fun onRefreshSearchButtonPressed(searchRequest: String) {
        searchRequest(searchRequest)
    }


    private fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }

        latestSearchText = changedText

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            searchRequest(changedText)
        }
    }

    private fun searchRequest(searchText: String) {

        if (searchText.isNotEmpty()) {
            renderState(SearchState.Loading)
            viewModelScope.launch {
                interactor
                    .search(searchText)
                    .collect { pair ->
                        processResult(pair.first, pair.second)
                    }
            }
        }
    }


    private fun processResult(searchTracks: List<Vacancy>?, errorMessage: ErrorNetwork?) {
        val tracks = mutableListOf<Vacancy>()
        if (searchTracks != null) {
            tracks.addAll(searchTracks)
        }

        when {
            errorMessage != null -> {
                renderState(SearchState.Error)
            }

            tracks.isEmpty() -> {
                renderState(SearchState.EmptySearch)
            }

            else -> {
                renderState(SearchState.SearchContent(vacansys = tracks))
            }
        }
    }
    fun onDestroy() {
        searchJob?.cancel()
    }



    companion object {
        const val SEARCH_DEBOUNCE_DELAY = 2_000L
    }
}
