package ru.practicum.android.diploma.search.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.models.PaginationInfo
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.usecase.VacanciesInteractor
import ru.practicum.android.diploma.search.presentation.SearchScreenState

private const val PAGE_SIZE = 20

class VacancyListViewModel(
    private val vacanciesInteractor: VacanciesInteractor,
    private val application: Application,
) : AndroidViewModel(application) {

    private var _screenStateLiveData = MutableLiveData<SearchScreenState>()
    val screenStateLiveData: LiveData<SearchScreenState> = _screenStateLiveData

    private var _vacancyListStateLiveData = MutableLiveData<VacancyListState>()
    val vacancyListStateLiveData: LiveData<VacancyListState> = _vacancyListStateLiveData

    private var _currentResultsCountLiveData = MutableLiveData<Int>()
    val currentResultsCountLiveData: LiveData<Int> = _currentResultsCountLiveData

    private var paginationInfo = PaginationInfo(emptyList<Vacancy>(), 0, 0, 0)

    private var currentQueryMap: MutableMap<String, String> = mutableMapOf()

    init {
        _screenStateLiveData.value = SearchScreenState.IDLE
        _vacancyListStateLiveData.value = VacancyListState.Empty
        _currentResultsCountLiveData.value = 0
    }

    companion object {
        private const val TAG: String = "VacancyListViewModel"
    }

    fun initialSearch(query: String) {
        _screenStateLiveData.postValue(SearchScreenState.LOADING_NEW_LIST)
        val options: Map<String, String> = mapOf(
            "page" to "0",
            "per_page" to "${PAGE_SIZE}",
            "text" to query // locale нужно? only_with_salary и т.д. добавим по настройке фильтров
        )
        currentQueryMap = options as MutableMap<String, String>

        viewModelScope.launch(Dispatchers.IO) {
            vacanciesInteractor.searchVacancies(options).collect { response ->
                if (response.first != null) {
                    paginationInfo = response.first ?: paginationInfo
                    parseNewList(paginationInfo.items)
                } else {
                    parseError(response.second)
                }
            }
        }
    }

    private fun parseError(error: String?) {
        error?.let {
            _screenStateLiveData.postValue(SearchScreenState.NO_INTERNET_ERROR)
        }
    }

    fun loadNextPageRequest() {
        if (paginationInfo.page == paginationInfo.pages) {
            return
        }

        _screenStateLiveData.postValue(SearchScreenState.LOADING_NEW_PAGE)
        val currentList = (vacancyListStateLiveData.value as VacancyListState.Content).vacancies
        viewModelScope.launch(Dispatchers.IO) {
            currentQueryMap["page"] = (paginationInfo.page + 1).toString()
            vacanciesInteractor.searchVacancies(currentQueryMap).collect { response ->
                if (response.first != null) {
                    paginationInfo = response.first ?: paginationInfo
                    updateLists(currentList, paginationInfo.items)
                } else {
                    parseError(response.second)
                }
            }
        }
    }

    private fun updateLists(oldList: List<Vacancy>, newList: List<Vacancy>) {
        val combinedList = oldList.toMutableList()
        combinedList.addAll(newList)
        _screenStateLiveData.postValue(SearchScreenState.VACANCY_LIST_LOADED)
        _vacancyListStateLiveData.postValue(VacancyListState.Content(combinedList))
        _currentResultsCountLiveData.postValue(paginationInfo.found)
    }

    private fun parseNewList(list: List<Vacancy>) {
        if (list.isEmpty()) {
            _screenStateLiveData.postValue(SearchScreenState.FAILED_TO_FETCH_VACANCIES_ERROR)
            _vacancyListStateLiveData.postValue(VacancyListState.Empty)
            _currentResultsCountLiveData.postValue(paginationInfo.found)
        } else {
            _screenStateLiveData.postValue(SearchScreenState.VACANCY_LIST_LOADED)
            _vacancyListStateLiveData.postValue(VacancyListState.Content(list))
            _currentResultsCountLiveData.postValue(paginationInfo.found)
        }
    }

    fun emptyList() {
        _screenStateLiveData.postValue(SearchScreenState.IDLE)
    }

}
