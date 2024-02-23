package ru.practicum.android.diploma.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.model.SearchFilterParameters
import ru.practicum.android.diploma.search.domain.usecase.SearchVacancyUseCase
import ru.practicum.android.diploma.util.Resource

class SearchViewModel(private val searchVacancyUseCase: SearchVacancyUseCase) : ViewModel() {
    init {
        renderState(SearchState.Default)
    }

    private val stateLiveData = MutableLiveData<SearchState>()
    fun observeState(): LiveData<SearchState> = stateLiveData

    private fun renderState(state: SearchState) {
        stateLiveData.postValue(state)
    }

    fun initSearch(
        searchText: String,
        page: Int,
        filterParameters: SearchFilterParameters
    ) {
        if (searchText.isNotEmpty()) {
            renderState(SearchState.Loading)
            viewModelScope.launch(Dispatchers.IO) {
                searchVacancyUseCase
                    .execute(searchText, page, filterParameters)
                    .collect {
                        when (it) {
                            is Resource.Success -> {
                                stateLiveData.postValue(SearchState.Content(it.data))
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
        }
    }

    fun clearSearch() {
        stateLiveData.postValue(SearchState.Default)
    }
}
