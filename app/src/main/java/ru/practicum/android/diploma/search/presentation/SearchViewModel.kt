package ru.practicum.android.diploma.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.models.Resource
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.models.VacancySearchParams
import ru.practicum.android.diploma.search.presentation.models.UiScreenState
import ru.practicum.android.diploma.search.presentation.models.VacancyUi

class SearchViewModel(val searchInteractor: SearchInteractor) : ViewModel() {
    private val _uiState = MutableLiveData<UiScreenState>(UiScreenState.Default)
    val uiState: LiveData<UiScreenState> get() = _uiState

    private val _vacanciesList = MutableLiveData<List<VacancyUi>>(emptyList())

    fun searchRequest(params: VacancySearchParams) {
        viewModelScope.launch {
            searchInteractor.searchVacancies(params).collect { result ->
                renderState(result)
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
                _vacanciesList.value = _vacanciesList.value?.plus(vacanciesUi)
                _uiState.value = UiScreenState.Success(vacancies = vacanciesUi, found = result.found ?: 0)
            }
        }
    }

    private fun mappingVacancyToVacancyUi(vacancy: Vacancy): VacancyUi {
        return VacancyUi(
            id = vacancy.id,
            name = vacancy.name,
            areaName = vacancy.areaName,
            salary = vacancy.salary.toString(),
            employerName = vacancy.employer.name ?: "",
            logoUrl = vacancy.employer.logoUrl
        )
    }

}
