package ru.practicum.android.diploma.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.common.SearchResult
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.search.api.VacanciesInteractor
import ru.practicum.android.diploma.util.SingleEventLiveData

class SearchViewModel(
    private val vacanciesInteractor: VacanciesInteractor
) : ViewModel() {

    private val searchResultData: MutableLiveData<SearchResult> =
        MutableLiveData(SearchResult.Empty)

    fun searchResultLiveData(): LiveData<SearchResult> = searchResultData

    private val openVacancyTrigger = SingleEventLiveData<Long>()
    fun getVacancyTrigger(): SingleEventLiveData<Long> = openVacancyTrigger

    fun clearSearchResults() {
        searchResultData.postValue(SearchResult.Empty)
    }

    fun searchVacancies(text: String?) {
        searchResultData.postValue(SearchResult.Loading)

        viewModelScope.launch {
            vacanciesInteractor
                .searchVacancies(text)
                .collect { result ->
                    resultHandle(result)
                    /*
                   еще потом докинуть условие в каком случае нет интернета
                   то есть в каком случае возращать SearchResult.NoConnection (задача 27)
                    */
                }
        }
    }

    private fun resultHandle(result: Resource<List<Vacancy>>) {
        if (result.errorCode != null) {
            if (result.errorCode == -1) {
                searchResultData.postValue(SearchResult.NoConnection)
            } else {
                searchResultData.postValue(SearchResult.Error)
            }

        } else if (result.value != null) {
            if (result.value.isEmpty()) {
                searchResultData.postValue(SearchResult.NotFound)
            } else {
                searchResultData.postValue(SearchResult.SearchVacanciesContent(result.value.size, result.value))
            }
        }
    }

    fun onVacancyClicked(vacancyId: Long) {
        openVacancyTrigger.value = vacancyId
    }

}
