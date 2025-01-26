package ru.practicum.android.diploma.ui.vacancydetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.vacancydetails.api.VacancyDetailsInteractor

class VacancyViewModel(
    private val vacancyDetailsInteractor: VacancyDetailsInteractor,
) : ViewModel() {

    // Временное решение для кнопок "Поделиться" и "Избранное"
    private val _vacancy = MutableLiveData<Vacancy>()
    val vacancy: LiveData<Vacancy> get() = _vacancy

    private val _isFavorite = MutableLiveData<Boolean>(false)
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    fun onFavoriteClicked() {
        _isFavorite.value = _isFavorite.value?.not()
    }

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
//            vacancyDetailsInteractor
//                .getVacancyDetails(id)
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
