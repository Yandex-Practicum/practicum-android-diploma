package ru.practicum.android.diploma.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.common.SearchResult
import ru.practicum.android.diploma.domain.search.usecase.GetVacanciesUseCase

class SearchViewModel(
    private val getVacanciesUseCase: GetVacanciesUseCase,
) : ViewModel() {

    private val searchResultData: MutableLiveData<SearchResult> =
        MutableLiveData(SearchResult.Empty)

    fun searchResultLiveData(): LiveData<SearchResult> = searchResultData

//    init {
//        searchTracks()
//    }
//
//    private fun searchTracks() {
//        var searchResult: SearchResult = SearchResult.Loading
//        searchResultData.postValue(searchResult)
//
//        viewModelScope.launch {
//            getVacanciesUseCase
//                .execute()
//                .collect { result ->
//                    searchResult = if (result != null) {
//                        if (result.found == 0) {
//                            SearchResult.NotFound
//                        } else {
//                            SearchResult.SearchVacanciesContent(
//                                result.found,
//                                result.items
//                            )
//
//                        }
//                    } else {
//                        SearchResult.Error
//                    }
//                    searchResultData.postValue(searchResult)
//                }
//        }
//    }
}
