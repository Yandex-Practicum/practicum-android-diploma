package ru.practicum.android.diploma.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.search.VacanciesSearchRepository
import ru.practicum.android.diploma.domain.filter.FiltersRepository
import ru.practicum.android.diploma.domain.models.Filters
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy
import ru.practicum.android.diploma.domain.models.vacacy.VacancyResponse

class SearchViewModel(
    private val vacancySearchRepository: VacanciesSearchRepository,
    private val filtersRepository: FiltersRepository,
) : ViewModel() {

    var lastQuery = ""
    private var currentPage = 0
    private var maxPagers = 0
    private var vacanciesList = mutableListOf<Vacancy>()
    private val stateLiveData = MutableLiveData<SearchViewState>()

    fun observeState(): LiveData<SearchViewState> = stateLiveData

    // Флоу со значинием не пустые ли фильтры, для отображения кнопки фильтров
    val isExistFiltersFlow = flow {
        filtersRepository.getFiltersFlow().map {
            it != Filters()
        }.collect {
            emit(it)
        }
    }

    // Флоу с фильтрами
    private val filtersFlow = flow {
        filtersRepository.getFiltersFlow().collect {
            emit(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        filtersRepository.cancelJob()
    }

    fun onLastItemReached() {
        if (currentPage < maxPagers && stateLiveData.value is SearchViewState.Content) {
            search(lastQuery)
        }
    }

    fun setDefaultState() {
        stateLiveData.value = SearchViewState.Default
    }

    fun clearPagingInfo() {
        lastQuery = ""
        currentPage = 0
        maxPagers = 0
        vacanciesList = mutableListOf()
    }

    fun search(text: String) {
        //stateLiveData.value = SearchViewState.Loading
        stateLiveData.value = SearchViewState.RecyclerLoading
        viewModelScope.launch {
            filtersFlow.collect { filters ->
                vacancySearchRepository.getVacancies(text, currentPage, filters).collect {
                    if (it.first != null) {
                        val response = (it.first as VacancyResponse)
                        maxPagers = response.pages
                        currentPage = response.page + 1
                        if (response.items.isEmpty()) {
                            stateLiveData.postValue(SearchViewState.EmptyVacancies)
                        } else {
                            //vacanciesList.addAll(response.items)
                            stateLiveData.postValue(SearchViewState.Content(response.items, response.found))
                        }

                    } else if (it.second != null) {
                        stateLiveData.postValue(SearchViewState.NoInternet)
                    }
                }
            }
        }
        lastQuery = text
    }
}
