package ru.practicum.android.diploma.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.SearchInteractor
import ru.practicum.android.diploma.domain.models.ErrorNetwork
import ru.practicum.android.diploma.domain.models.Vacancy


class SearchViewModel(
    private var searchInteractor: SearchInteractor
) : ViewModel() {


    private val stateLiveData = MutableLiveData<SearchState>()

    private fun renderState(state: SearchState) {
        stateLiveData.postValue(state)
    }

    fun observeState(): LiveData<SearchState> = stateLiveData
    fun getStateLiveData(): LiveData<SearchState> {
        return stateLiveData
    }


   // private var trackResultList: MutableLiveData<List<Vacancy>?> = MutableLiveData<List<Vacancy>?>()
/*
    fun searchRequesting(searchExpression: String) {
        if (searchExpression.isNotEmpty()) {
            stateLiveData.postValue(SearchState.Loading)
            viewModelScope.launch {
                stateLiveData.postValue(SearchState.Loading)
                try {
                    searchInteractor.search(searchExpression).collect {
                        when (it.message) {
                            "CONNECTION_ERROR" -> stateLiveData.postValue(SearchState.ConnectionError)
                            "SERVER_ERROR" -> stateLiveData.postValue(SearchState.NothingFound)
                            else -> {
                                trackResultList.postValue(it.data)
                                stateLiveData.postValue(
                                    if (it.data.isNullOrEmpty())
                                        SearchState.NothingFound
                                    else SearchState.SearchIsOk(it.data)
                                )
                            }
                        }
                    }
                } catch (error: Error) {
                    stateLiveData.postValue(SearchState.ConnectionError)
                }
            }
        }
    }


 */

    fun searchRequest(searchText: String) {

        if (searchText.isNotEmpty()) {
            renderState(SearchState.Loading)
            viewModelScope.launch {
                searchInteractor
                    .search(searchText)
                    .collect { pair ->
                        processResult(pair.first, pair.second)
                    }
            }
        }
    }
    val vacancys = mutableListOf<Vacancy>()
    private fun processResult(searchVacancys: List<Vacancy>?, errorMessage: ErrorNetwork?) {

        if (searchVacancys != null) {
            vacancys.addAll(searchVacancys)
        }

        when {
            errorMessage != null -> {
                renderState(SearchState.Error)
            }

            vacancys.isEmpty() -> {
                renderState(SearchState.EmptySearch)
            }

            else -> {
                renderState(SearchState.SearchContent(vacancys = vacancys))
            }
        }
    }
}
