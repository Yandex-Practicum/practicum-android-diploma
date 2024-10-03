package ru.practicum.android.diploma.search.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.usecase.VacanciesInteractor
import ru.practicum.android.diploma.search.presentation.SearchScreenState


private const val PAGE_SIZE = 20

class VacancyListViewModel(
    private val vacanciesInteractor: VacanciesInteractor,
    application: Application,
) : AndroidViewModel(application) {

    private var _screenStateLiveData = MutableLiveData<SearchScreenState>()
    val screenStateLiveData: LiveData<SearchScreenState> = _screenStateLiveData

    private var _vacancyListStateLiveData = MutableLiveData<VacancyListState>()
    val vacancyListStateLiveData: LiveData<VacancyListState> = _vacancyListStateLiveData

    private var currentLastResultIndex = 0

    private var currentQueryMap: MutableMap<String, String> = mutableMapOf()


    init {
        _screenStateLiveData.value = SearchScreenState.IDLE
        _vacancyListStateLiveData.value = VacancyListState.Empty
    }

    fun initialSearch(query: String) {
        _screenStateLiveData.value = SearchScreenState.LOADING_NEW_LIST
        val options: Map<String, String> = mapOf(
            "page" to "0",
            "per_page" to "${PAGE_SIZE}",
            "text" to query //locale нужно? only_with_salary и т.д. добавим по настройке фильтров
        )
        currentQueryMap = options as MutableMap<String, String>

        viewModelScope.launch(Dispatchers.IO) {

            vacanciesInteractor.searchVacancies(options).collect { response ->
                when (response.first != null) {
                    true -> parseNewList(response.first as List<Vacancy>)
                    false -> parseError(response.second)
                }
            }
        }

    }

    private fun parseError(error: String?) {
        // логика по ошибкам тут пока заглушка
        _screenStateLiveData.value = SearchScreenState.NO_INTERNET_ERROR
    }

    fun loadNextPageRequest() {
        _screenStateLiveData.value = SearchScreenState.LOADING_NEW_PAGE

        if (currentLastResultIndex != (vacancyListStateLiveData.value as VacancyListState.Content).vacancies.size) {
            val currentList = (vacancyListStateLiveData.value as VacancyListState.Content).vacancies
            viewModelScope.launch(Dispatchers.IO) {
                currentQueryMap["page"] = (currentLastResultIndex / PAGE_SIZE).toString()
                vacanciesInteractor.searchVacancies(currentQueryMap).collect { response ->
                    when (response.first != null) {
                        true -> updateList(
                            currentList,
                            response.first as List<Vacancy>
                        )

                        false -> parseError(response.second)
                    }
                }
            }
        }
    }

    private fun updateList(oldList: List<Vacancy>, newList: List<Vacancy>) {
        val combinedList = oldList.toMutableList()
        combinedList.addAll(newList)
        _screenStateLiveData.value = SearchScreenState.VACANCY_LIST_LOADED
        _vacancyListStateLiveData.value = VacancyListState.Content(combinedList)
        displayLogsForTesting(newList)
    }

    private fun parseNewList(list: List<Vacancy>) {
        if (list.isEmpty()) {
            _screenStateLiveData.postValue(SearchScreenState.FAILED_TO_FETCH_VACANCIES_ERROR)
            _vacancyListStateLiveData.postValue(VacancyListState.Empty)
        } else {
            _screenStateLiveData.postValue(SearchScreenState.VACANCY_LIST_LOADED)
            _vacancyListStateLiveData.postValue(VacancyListState.Content(list))
            displayLogsForTesting(list)
        }
    }

    private fun displayLogsForTesting(list: List<Vacancy>) {
        var count = 0
        for (item in list) {
            Log.d("TEST", "${count++}. ${item.title}")
        }
    }

}
