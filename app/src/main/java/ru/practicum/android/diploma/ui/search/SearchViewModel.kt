package ru.practicum.android.diploma.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.search.SearchPagingRepository
import ru.practicum.android.diploma.domain.api.search.VacanciesSearchRepository
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy
import ru.practicum.android.diploma.domain.models.vacacy.VacancyResponse

class SearchViewModel(
    private val vacancySearchRepository: VacanciesSearchRepository,
    private val searchPagingRepository: SearchPagingRepository
) : ViewModel() {

    private val stateLiveData = MutableLiveData<Int>()

    fun observeState(): LiveData<Int> = stateLiveData

    fun search(text: String): Flow<PagingData<Vacancy>> {
        viewModelScope.launch {
            vacancySearchRepository.getVacancies(text, 1).collect {
                if (it.first != null) {
                    Log.e("searchFound()", (it.first as VacancyResponse).found.toString())
                    stateLiveData.postValue((it.first as VacancyResponse).found)
                }
            }
        }
        Log.e("searchPading", text)
        return searchPagingRepository.getSearchPaging(text).cachedIn(viewModelScope)
    }
}
