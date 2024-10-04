package ru.practicum.android.diploma.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.models.Resource
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.models.VacancySearchParams
import ru.practicum.android.diploma.search.presentation.models.UiScreenState
import ru.practicum.android.diploma.search.presentation.models.VacancyUi
import ru.practicum.android.diploma.util.formatSalary

class SearchViewModel(val searchInteractor: SearchInteractor) : ViewModel() {
    private val _uiState = MutableLiveData<UiScreenState>(UiScreenState.Default)
    val uiState: LiveData<UiScreenState> get() = _uiState

    private val _vacanciesList = MutableLiveData<List<VacancyUi>>(emptyList())
    private val _searchQuery = MutableLiveData<String>("")

    private var searchJob: Job? = null

    // Переменные пагинации
    private var currentPage = 0
    private var maxPages = Int.MAX_VALUE
    private var isNextPageLoading = false

    fun onSearchQueryChanged(query: String) {
        currentPage = 0
        maxPages = Int.MAX_VALUE
        _vacanciesList.value = emptyList()
        _searchQuery.value = query
        if (query == "") {
            searchJob?.cancel()
            _uiState.value = UiScreenState.Default
            return
        }
        val params = VacancySearchParams(query = query, page = currentPage)
        _uiState.value = UiScreenState.Loading
        searchRequest(params)
    }

    fun onLastItemReached() {
        if (isNextPageLoading || currentPage >= maxPages - 1) return
        isNextPageLoading = true
        val query = _searchQuery.value ?: return
        val params = VacancySearchParams(query = query, page = currentPage + 1)
        searchRequest(params)
    }

    private fun searchRequest(params: VacancySearchParams) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            searchInteractor.searchVacancies(params).collect { result ->
                renderState(result)
                isNextPageLoading = false
            }
        }
    }

    private fun renderState(result: Resource<List<Vacancy>>) {
        when (result) {
            is Resource.NoInternetError -> _uiState.value = UiScreenState.NoInternetError
            is Resource.ServerError -> _uiState.value = UiScreenState.ServerError
            is Resource.Success -> if (result.data.isEmpty()) {
                _uiState.value = UiScreenState.Empty
            } else {
                val vacanciesUi = result.data.map { vacancy ->
                    mappingVacancyToVacancyUi(vacancy)
                }
                currentPage = result.page ?: currentPage
                maxPages = result.pages ?: maxPages
                _vacanciesList.value = _vacanciesList.value?.plus(vacanciesUi)
                _uiState.value = UiScreenState.Success(
                    vacancies = _vacanciesList.value ?: emptyList(),
                    found = result.found ?: 0
                )
            }
        }
    }

    private fun mappingVacancyToVacancyUi(vacancy: Vacancy): VacancyUi {
        return VacancyUi(
            id = vacancy.id,
            name = getName(vacancy.name, vacancy.areaName),
            salary = vacancy.salary.formatSalary(),
            employerName = vacancy.employer.name ?: "",
            logoUrl = vacancy.employer.logoUrl
        )
    }

    private fun getName(name: String, areaName: String): String {
        return if (areaName.isEmpty()) {
            name
        } else {
            "$name, $areaName"
        }
    }

}
