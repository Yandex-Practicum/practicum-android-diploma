package ru.practicum.android.diploma.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.models.SearchParams
import ru.practicum.android.diploma.domain.search.models.SearchScreenState
import java.io.IOException

class SearchViewModel(
    private val searchInteractor: SearchInteractor
) : ViewModel() {

    private val searchScreenStateLiveData = MutableLiveData<SearchScreenState>()

    fun getSearchScreenStateLiveData(): LiveData<SearchScreenState> = searchScreenStateLiveData

    fun getVacancies(searchParams: SearchParams) {
        viewModelScope.launch {
            searchScreenStateLiveData.postValue(SearchScreenState.Loading)
            try {
                searchInteractor.getVacancies(searchParams).collect { listOfVacancies ->
                    if (listOfVacancies.isNotEmpty()) {
                        searchScreenStateLiveData.postValue(SearchScreenState.Content(listOfVacancies))
                    } else {
                        searchScreenStateLiveData.postValue(SearchScreenState.NotFound)
                    }
                }
            } catch (e: IOException) {
                searchScreenStateLiveData.postValue(SearchScreenState.NetworkError)
            } catch (e: Exception) {
                when (e.message) {
                    "Server Error 500" -> {
                        searchScreenStateLiveData.postValue(SearchScreenState.ServerError)
                    }
                    "Not Found 404" -> {
                        searchScreenStateLiveData.postValue(SearchScreenState.NotFound)
                    }
                    else -> {
                        searchScreenStateLiveData.postValue(SearchScreenState.Error(e.message ?: "Unknown Error"))
                    }
                }
            }
        }
    }
}
