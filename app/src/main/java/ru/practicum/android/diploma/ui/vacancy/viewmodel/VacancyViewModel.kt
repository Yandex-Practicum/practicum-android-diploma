package ru.practicum.android.diploma.ui.vacancy.viewmodel

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.vacancy.api.VacancyInteractor

class VacancyViewModel(
    private val vacancyInteractor: VacancyInteractor,
) : ViewModel() {

//    private val searchResultData: MutableLiveData<SearchResult> =
//        MutableLiveData(SearchResult.Empty)
//
//    init {
//        // тестовый запрос рандомной вакансии - потом удалить
//        getVacancy("116183826")
//    }
//
//    private fun getVacancy(id: String) {
//        if (id.isEmpty()) {
//            return
//        }
//
//        var searchResult: SearchResult = SearchResult.Loading
//        searchResultData.postValue(searchResult)
//
//        viewModelScope.launch {
//            vacancyInteractor
//                .execute(id)
//                .collect { result ->
//                    searchResult = if (result != null) {
//                        /*
//                        в if'е подкинуть условие: в каком случае вакансия не найдена (задача 27)
//                         */
//                        if (true) {
//                            SearchResult.NotFound
//                        } else {
//                            SearchResult.GetVacancyContent(
//                                result,
//                            )
//
//                        }
//                    } else {
//                        SearchResult.Error
//                    }
//                    /*
//                    еще потом докинуть условие в каком случае нет интернета
//                    то есть в каком случае возращать SearchResult.NoConnection (задача 27)
//                     */
//                    searchResultData.postValue(searchResult)
//                }
//        }
//    }
}
