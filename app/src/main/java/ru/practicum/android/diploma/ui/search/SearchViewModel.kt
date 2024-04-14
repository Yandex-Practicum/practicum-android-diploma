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

    fun search(text: String) {
        viewModelScope.launch {
            filtersFlow.collect { filters ->
                vacancySearchRepository.getVacancies(text, 0, filters).collect {
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
        }
    }
}
