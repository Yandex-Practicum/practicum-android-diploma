package ru.practicum.android.diploma.ui.search.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.models.SearchParams
import ru.practicum.android.diploma.domain.search.models.SearchScreenState
import java.io.IOException

class SearchViewModel(
    private val searchInteractor: SearchInteractor
) : ViewModel() {

    companion object {
        private const val HTTP_NOT_FOUND = 404
        private const val HTTP_SERVER_ERROR = 500
    }

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
                Log.e("SearchViewModel", "Network error", e)
                searchScreenStateLiveData.postValue(SearchScreenState.NetworkError)
            } catch (e: HttpException) {
                when (e.code()) {
                    HTTP_NOT_FOUND -> {
                        searchScreenStateLiveData.postValue(SearchScreenState.NotFound)
                    }

                    HTTP_SERVER_ERROR -> {
                        searchScreenStateLiveData.postValue(SearchScreenState.ServerError)
                    }

                    else -> {
                        searchScreenStateLiveData.postValue(SearchScreenState.Error("HTTP Error: ${e.code()}"))
                    }
                }
            }
        }
    }
}
