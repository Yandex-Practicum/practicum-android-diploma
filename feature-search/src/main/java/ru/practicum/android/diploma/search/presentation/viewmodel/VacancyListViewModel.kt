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
import ru.practicum.android.diploma.search.domain.models.sp.FilterSearch
import ru.practicum.android.diploma.search.domain.usecase.VacanciesInteractor
import ru.practicum.android.diploma.search.presentation.SearchScreenState
import ru.practicum.android.diploma.search.presentation.viewmodel.state.VacancyListState

private const val INTERNET_ERROR: String = "Check network connection"
private const val PAGE_SIZE = 20
private const val INDUSTRY_ID = "industry"
private const val SALARY = "salary"
private const val AREA_ID = "area"
private const val ONLY_WITH_SALARY = "only_with_salary"

internal class VacancyListViewModel(
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
    private var currentQuery: String = ""
    private var currentPage: Int = 0

    private val queryFilter: MutableMap<String, String> = mutableMapOf()

    init {
        _screenStateLiveData.value = SearchScreenState.IDLE
        _vacancyListStateLiveData.value = VacancyListState.Empty
        _currentResultsCountLiveData.value = 0
        viewModelScope.launch(Dispatchers.IO) {
            initQueryFilter(vacanciesInteractor.getDataFilter())
        }
    }

    private fun initQueryFilter(filterSearch: FilterSearch) {
        filterSearch.branchOfProfession?.id?.let { queryFilter.put(INDUSTRY_ID, it) }
        filterSearch.expectedSalary?.let { queryFilter.put(SALARY, it) }
        filterSearch.doNotShowWithoutSalary.let { queryFilter.put(ONLY_WITH_SALARY, it.toString()) }
        // !!!
        filterSearch.placeSearch?.let { place ->
            place.idRegion?.let { queryFilter.put(AREA_ID, it) }
        }
        filterSearch.placeSearch?.let { place ->
            place.idCountry?.let { queryFilter.put(AREA_ID, it) }
        }
    }

    fun initialSearch(query: String) {
        _screenStateLiveData.postValue(SearchScreenState.LOADING_NEW_LIST)
        currentQuery = query

        viewModelScope.launch(Dispatchers.IO) {
            vacanciesInteractor.searchVacancies(
                page = "0",
                perPage = "${PAGE_SIZE}",
                queryText = query,
                industry = queryFilter.get(INDUSTRY_ID),
                salary = queryFilter.get(SALARY),
                area = queryFilter.get(AREA_ID),
                onlyWithSalary = queryFilter.get(ONLY_WITH_SALARY).toBoolean()
            ).collect { response ->
                if (response.first != null) {
                    paginationInfo = response.first ?: paginationInfo
                    parseNewList(paginationInfo.items)
                } else {
                    if (response.second == INTERNET_ERROR) {
                        parseError(SearchScreenState.Error.NO_INTERNET_ERROR)
                    } else {
                        parseError(SearchScreenState.Error.SERVER_ERROR)
                    }
                }
            }
        }
    }

    private fun parseError(state: SearchScreenState) {
        _screenStateLiveData.postValue(state)
    }

    fun loadNextPageRequest() {
        if (paginationInfo.page == paginationInfo.pages) {
            return
        }

        _screenStateLiveData.postValue(SearchScreenState.LOADING_NEW_PAGE)
        val currentList = (vacancyListStateLiveData.value as VacancyListState.Content).vacancies
        viewModelScope.launch(Dispatchers.IO) {
            vacanciesInteractor.searchVacancies(
                page = (currentPage + 1).toString(),
                perPage = "${PAGE_SIZE}",
                queryText = currentQuery,
                industry = queryFilter.get("industry_id"),
                salary = queryFilter.get("salary"),
                area = queryFilter.get("area_id"),
                onlyWithSalary = queryFilter.get("only_with_salary").toBoolean()
            ).collect { response ->
                if (response.first != null) {
                    paginationInfo = response.first ?: paginationInfo
                    updateLists(currentList, paginationInfo.items)
                } else {
                    if (response.second == INTERNET_ERROR) {
                        parseError(SearchScreenState.Error.NEW_PAGE_NO_INTERNET_ERROR)
                    } else {
                        parseError(SearchScreenState.Error.NEW_PAGE_SERVER_ERROR)
                    }
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
            _screenStateLiveData.postValue(SearchScreenState.Error.FAILED_TO_FETCH_VACANCIES_ERROR)
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

    fun createTitle(model: Vacancy): String {
        return model.title + ", " + model.area.name + ""
    }
}
