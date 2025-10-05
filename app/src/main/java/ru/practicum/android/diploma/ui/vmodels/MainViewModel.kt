package ru.practicum.android.diploma.ui.vmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.ui.models.SearchState

class MainViewModel : ViewModel() {
    private val _searchState = MutableLiveData<SearchState>(SearchState.Idle)
    val observeSearchState: LiveData<SearchState> = _searchState

    fun setNothingState() {
        _searchState.value = SearchState.Nothing
    }

    fun setIdleState() {
        _searchState.value = SearchState.Idle
    }

    fun search(value: String) {
        _searchState.value = SearchState.Loading
    }
}
