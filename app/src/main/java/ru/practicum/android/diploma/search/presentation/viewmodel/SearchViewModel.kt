package ru.practicum.android.diploma.search.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.interactor.SearchInteractor
import ru.practicum.android.diploma.search.domain.model.SearchViewState
import ru.practicum.android.diploma.search.domain.model.VacancyItems
import ru.practicum.android.diploma.search.domain.model.VacancyList

class SearchViewModel(
    private val searchInteractor: SearchInteractor
) : ViewModel() {

    private var currentPage: Int = 0
    private var maxPages: Int? = 0
    private var searchQuery: String = ""
    private var vacancyList = mutableListOf<VacancyItems>()
    private var isNextPageLoading: Boolean = false

    private val stateLiveData = MutableLiveData<SearchViewState>()
    fun observeState(): LiveData<SearchViewState> = stateLiveData

    fun searchVacancy(searchQuery: String) {
        isNextPageLoading = true
        if (searchQuery.isNotEmpty()) {
            isNextPageLoading = true
            this.searchQuery = searchQuery
            renderState(SearchViewState.Loading)
            viewModelScope.launch {
                searchInteractor
                    .searchVacancy(searchQuery, currentPage)
                    .collect { viewState ->
                        renderState(viewState)
                    }
            }
        }
        isNextPageLoading = false
    }

    fun onLastItemReached(){
        if(currentPage != maxPages && maxPages != 0){
            currentPage += 1
               searchVacancy(searchQuery)
        }
    }

    fun showVacancyDetails(vacancyItems: VacancyItems){

    }

    private fun renderState(state: SearchViewState) {
        if (state is SearchViewState.Success) {
            this.maxPages = state.vacancyList.pages
            this.vacancyList += state.vacancyList.items
            val vacancyList = state.vacancyList.copy(items = this.vacancyList)
            stateLiveData.postValue(
                SearchViewState.Success(vacancyList)
            )
        } else {
            stateLiveData.postValue(state)
        }
    }


}
