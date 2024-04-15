package ru.practicum.android.diploma.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.search.SearchPagingRepository
import ru.practicum.android.diploma.domain.api.search.VacanciesSearchRepository
import ru.practicum.android.diploma.domain.filter.FiltersRepository
import ru.practicum.android.diploma.domain.models.Filters
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy
import ru.practicum.android.diploma.domain.models.vacacy.VacancyResponse

class SearchViewModel(
    private val vacancySearchRepository: VacanciesSearchRepository,
    private val searchPagingRepository: SearchPagingRepository,
    private val filtersRepository: FiltersRepository,
) : ViewModel() {

    var isCrossPressed = false
    var lastQuery = ""
    private val foundLiveData = MutableLiveData<Int>()

    fun observeState(): LiveData<Int> = foundLiveData

    // Флоу со значинием не пустые ли фильтры, для отображения кнопки фильтров
    val isExistFiltersFlow = flow {
        filtersRepository.getFiltersFlow().map {
            it != Filters()
        }.collect {
            emit(it)
        }
    }

    // Флоу с фильтрами
    val filtersFlow = flow {
        filtersRepository.getFiltersFlow().collect {
            emit(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        filtersRepository.cancelJob()
    }

    fun search(text: String, filters: Filters): Flow<PagingData<Vacancy>> {
        searchFoundVacancies(text, filters)
        Log.d("searchPaging", text)
        return searchPagingRepository.getSearchPaging(text, filters).cachedIn(viewModelScope)
    }

    private fun searchFoundVacancies(text: String, filters: Filters) {
        viewModelScope.launch {
            vacancySearchRepository.getVacancies(text, 0, filters).collect { pair ->
                if (pair.first != null) {
                    Log.d("searchFound", (pair.first as VacancyResponse).found.toString())
                    foundLiveData.postValue((pair.first as VacancyResponse).found)
                }
            }
        }
    }

    fun clearFoundLiveData() {
        foundLiveData.value = -1
    }
}
