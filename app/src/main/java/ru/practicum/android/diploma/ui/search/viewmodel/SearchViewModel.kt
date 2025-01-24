package ru.practicum.android.diploma.ui.search.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.common.SearchResult
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.search.api.VacanciesInteractor

class SearchViewModel(
    private val vacanciesInteractor: VacanciesInteractor
) : ViewModel() {

    private val searchResultData: MutableLiveData<SearchResult> =
        MutableLiveData(SearchResult.Empty)

    fun searchResultLiveData(): LiveData<SearchResult> = searchResultData

    fun searchVacancies() {
        var searchResult: SearchResult = SearchResult.Loading
        searchResultData.postValue(searchResult)

        viewModelScope.launch {
            vacanciesInteractor
                .execute()
                .collect { result ->
                    searchResult = if (result != null) {
                        if (result.found == 0) {
                            SearchResult.NotFound
                        } else {
                            SearchResult.SearchVacanciesContent(
                                result.found,
                                result.items
                            )

                        }
                    } else {
                        SearchResult.Error
                    }
                    /*
                   еще потом докинуть условие в каком случае нет интернета
                   то есть в каком случае возращать SearchResult.NoConnection (задача 27)
                    */
                    searchResultData.postValue(searchResult)
                }
        }
    }

    fun onVacancyClicked(vacancy: Vacancy) {
        Log.i("isClicked", "Пользователь нажал на вакансию")
    }

}
