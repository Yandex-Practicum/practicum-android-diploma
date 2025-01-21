package ru.practicum.android.diploma.search.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.interactor.SearchInteractor
import ru.practicum.android.diploma.search.domain.model.SearchQueryParams
import ru.practicum.android.diploma.search.domain.model.SearchViewState

class SearchViewModel(
    private val searchInteractor: SearchInteractor
) : ViewModel() {

    private val searchScreenLiveData = MutableLiveData<SearchViewState>()
    fun getSearchLiveData(): LiveData<SearchViewState> = searchScreenLiveData
}
