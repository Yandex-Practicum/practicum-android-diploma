package ru.practicum.android.diploma.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
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

    private var filters: Filters = Filters()

    fun updateFilters() {
        viewModelScope.launch {
            filtersRepository.getFiltersFlow().collect {
                filters = it
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        filtersRepository.cancelJob()
    }

    fun search(text: String): Flow<PagingData<Vacancy>> {
        viewModelScope.launch {
            vacancySearchRepository.getVacancies(text, 0, filters).collect {
                if (it.first != null) {
                    Log.d("searchFound", (it.first as VacancyResponse).found.toString())
                    foundLiveData.postValue((it.first as VacancyResponse).found)
                }
            }
        }
        Log.d("searchPaging", text)
        return searchPagingRepository.getSearchPaging(text, filters).cachedIn(viewModelScope)
    }

    fun clearFoundLiveData() {
        foundLiveData.value = -1
    }
}
