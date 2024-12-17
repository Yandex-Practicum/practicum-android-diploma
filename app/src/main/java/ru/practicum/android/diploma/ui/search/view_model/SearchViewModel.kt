package ru.practicum.android.diploma.ui.search.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.models.SearchParams
import ru.practicum.android.diploma.domain.search.models.SearchScreenState

class SearchViewModel(
    private val searchInteractor: SearchInteractor
) : ViewModel() {

    private val searchScreenStateLiveData = MutableLiveData<SearchScreenState>()

    fun getSearchScreenStateLiveData(): LiveData<SearchScreenState> = searchScreenStateLiveData

    fun getVacancies(searchParams: SearchParams) {
        viewModelScope.launch {
            searchInteractor
                .getVacancies(searchParams)
                .collect { listOfVacancies ->
                    searchScreenStateLiveData.postValue(
                        SearchScreenState(listOfVacancies)
                    )
                }
        }
    }
}
