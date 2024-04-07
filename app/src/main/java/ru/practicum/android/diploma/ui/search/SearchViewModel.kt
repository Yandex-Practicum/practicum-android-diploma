package ru.practicum.android.diploma.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.search.SearchPagingRepository

class SearchViewModel(
    //private val vacancySearchRepository: VacanciesSearchRepository
    private val searchPagingRepository: SearchPagingRepository
) : ViewModel() {

    private val stateLiveData = MutableLiveData<SearchViewState>()

    fun observeState(): LiveData<SearchViewState> = stateLiveData

    fun search(text: String, page: Int) {
        viewModelScope.launch {
            stateLiveData.postValue(SearchViewState.Loading)
            searchPagingRepository.getSearchPaging(text).cachedIn(viewModelScope).collect {
                stateLiveData.postValue(SearchViewState.Content(it, 0))
            }
        }
    }

    /*fun search(text: String, page: Int) {
        viewModelScope.launch {
            stateLiveData.postValue(SearchViewState.Loading)
            vacancySearchRepository.getVacancies(text, page).collect {
                if (it.first != null) {
                    val response = (it.first as VacancyResponse)
                    if (response.items.isEmpty()) {
                        stateLiveData.postValue(SearchViewState.EmptyVacancies)
                    } else {
                        stateLiveData.postValue(SearchViewState.Content(response.items, response.found))
                    }

                } else if (it.second != null) {
                    stateLiveData.postValue(SearchViewState.NoInternet)
                }
            }
        }
    }*/
}
